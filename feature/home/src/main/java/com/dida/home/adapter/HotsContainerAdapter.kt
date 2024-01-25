package com.dida.home.adapter

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.dida.common.util.Constants
import com.dida.common.util.SLIDETYPE
import com.dida.common.widget.CirclePagerIndicatorDecoration
import com.dida.common.widget.smoothScrollToPosition
import com.dida.domain.main.model.HotItems
import com.dida.home.HomeActionHandler
import com.dida.home.databinding.HolderHotsContainerBinding
import java.lang.ref.WeakReference
import kotlin.math.abs

class HotsContainerAdapter(
    private val eventListener: HomeActionHandler
) : ListAdapter<HotItems.Contents, HotsContainerViewHolder>(HotsItemDiffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotsContainerViewHolder {
        return HotsContainerViewHolder(
            HolderHotsContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@HotsContainerAdapter.eventListener
                    val indicatorHeight = parent.context.resources.getDimensionPixelSize(com.dida.common.R.dimen.hots_indicator_height)
                    val activeColor: Int = ContextCompat.getColor(parent.context, com.dida.common.R.color.white)
                    val inactiveColor: Int = ContextCompat.getColor(parent.context, com.dida.common.R.color.surface6)
                    hotsViewPager.addItemDecoration(
                        CirclePagerIndicatorDecoration(
                            slideType = SLIDETYPE.CAROUSEL,
                            activeColor = activeColor,
                            inactiveColor = inactiveColor,
                            indicatorHeight = indicatorHeight
                        )
                    )
                }
        )
    }

    override fun onBindViewHolder(holder: HotsContainerViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1
}

class HotsContainerViewHolder(
    private val binding: HolderHotsContainerBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var targetPosition = 0
    private var contentSize = 0

    private val scrollFinishOffset = 0.001633987f

    private val handler = HotsContainerViewHandler(this)

    private val compositePageTransformer = CompositePageTransformer().apply {
        addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleX = 0.9f + r * 0.1f
        }
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            handler.removeMessages(MSG_START_SCROLL)
            handler.sendEmptyMessageDelayed(MSG_START_SCROLL, Constants.REPEAT_INTERVAL_MS)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            if ((position == contentSize - 2)) {
                with(binding.hotsViewPager) {
                    this.post { this.setCurrentItem(2, false) }
                }
            } else if ((position == 1) && (positionOffset <= scrollFinishOffset)) {
                binding.hotsViewPager.setCurrentItem(contentSize - 3, false)
            }
        }
    }

    init {
        binding.hotsViewPager.registerOnPageChangeCallback(onPageChangeCallback)
        handler.sendEmptyMessageDelayed(MSG_START_SCROLL, Constants.REPEAT_INTERVAL_MS)
    }

    fun bind(hotItems: HotItems.Contents) {
        if (hotItems.contents.size > 1) {
            contentSize = hotItems.contents.size + 4
            val items = with(hotItems.contents) {
                this.slice(this.size -2 until this.size) + this + this.slice(0 until 2)
            }
            binding.holderModel = HotItems.Contents(items)
            binding.hotsViewPager.apply {
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                setPageTransformer(compositePageTransformer)
            }
        } else {
            contentSize = hotItems.contents.size
            binding.holderModel = hotItems
        }
        binding.executePendingBindings()
        if (hotItems.contents.size > 1) binding.hotsViewPager.setCurrentItem(2, false)
    }

    fun handleMessage(msg: Message?) {
        if (msg?.what != MSG_START_SCROLL) return

        if (this.itemView.isShown) {
            val nextPosition = binding.hotsViewPager.currentItem + 1
            targetPosition = if (contentSize <= targetPosition) 0 else nextPosition
            binding.hotsViewPager.smoothScrollToPosition(
                item = targetPosition,
                duration = Constants.ROLLING_INTERVAL_MS,
                pagePxWidth = (this.itemView.width * (0.7f)).toInt()
            )
        }
    }

    private class HotsContainerViewHandler(viewHolder: HotsContainerViewHolder) : Handler(Looper.getMainLooper()) {
        private val holder: WeakReference<HotsContainerViewHolder> = WeakReference(viewHolder)
        override fun handleMessage(msg: Message) {
            val viewHolder = holder.get()
            viewHolder?.handleMessage(msg)
        }
    }

    companion object {
        private const val MSG_START_SCROLL = 0x0001
    }
}

internal object HotsItemDiffCallback : DiffUtil.ItemCallback<HotItems.Contents>() {
    override fun areItemsTheSame(oldItem: HotItems.Contents, newItem: HotItems.Contents) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: HotItems.Contents, newItem: HotItems.Contents) =
        oldItem == newItem
}
