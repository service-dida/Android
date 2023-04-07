package com.dida.community.adapter

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.dida.common.util.context
import com.dida.common.widget.CirclePagerIndicatorDecoration
import com.dida.common.widget.smoothScrollToPosition
import com.dida.community.HotCardActionHandler
import com.dida.community.databinding.HolderHotCardsContainerBinding
import com.dida.domain.model.main.HotCards
import java.lang.ref.WeakReference
import kotlin.math.abs


class HotCardsContainerAdapter(
    private val eventListener: HotCardActionHandler
) : ListAdapter<HotCards.Contents, HotCardsContainerViewHolder>(HotCardsDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotCardsContainerViewHolder {
        return HotCardsContainerViewHolder(
            HolderHotCardsContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                eventListener = this@HotCardsContainerAdapter.eventListener
                val indicatorHeight = parent.context.resources.getDimensionPixelSize(com.dida.common.R.dimen.hots_indicator_height)
                val activeColor: Int = ContextCompat.getColor(parent.context, com.dida.common.R.color.white)
                val inactiveColor: Int = ContextCompat.getColor(parent.context, com.dida.common.R.color.surface6)
                hotCardsViewpager.addItemDecoration(
                    CirclePagerIndicatorDecoration(
                        isViewPager = true,
                        isInfiniteScroll = true,
                        activeColor = activeColor,
                        inactiveColor = inactiveColor,
                        indicatorHeight = indicatorHeight
                    )
                )
            }
        )
    }

    override fun onBindViewHolder(holder: HotCardsContainerViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1
}

class HotCardsContainerViewHolder(
    private val binding: HolderHotCardsContainerBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val repeatIntervalMs = 2850L
    private val rollingIntervalMs = repeatIntervalMs/3

    private var targetPosition = 0
    private var contentSize = 0

    private val handler = HotCardsContainerViewHandler(this)

    private val compositePageTransformer = CompositePageTransformer().apply {
        addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            handler.removeMessages(MSG_START_SCROLL)
            handler.sendEmptyMessageDelayed(MSG_START_SCROLL, repeatIntervalMs)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            if ((position == contentSize - 2)) {
                binding.hotCardsViewpager.setCurrentItem(2, false)
            } else if ((position == 1) && (positionOffset <= 0.001633987f)) {
                binding.hotCardsViewpager.setCurrentItem(contentSize - 3, false)
            }
        }
    }

    init {
        binding.hotCardsViewpager.registerOnPageChangeCallback(onPageChangeCallback)
        handler.sendEmptyMessageDelayed(MSG_START_SCROLL, repeatIntervalMs)
    }

    fun bind(hotCardsItem: HotCards.Contents) {
        if (hotCardsItem.contents.size > 1) {
            contentSize = hotCardsItem.contents.size + 4
            val items = listOf(
                hotCardsItem.contents[hotCardsItem.contents.size-2],
                hotCardsItem.contents.last()
            ) + hotCardsItem.contents + hotCardsItem.contents.first() + hotCardsItem.contents[1]
            binding.holderModel = HotCards.Contents(items)
            binding.hotCardsViewpager.apply {
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                setPageTransformer(compositePageTransformer)
            }
        } else {
            contentSize = hotCardsItem.contents.size
            binding.holderModel = hotCardsItem
        }
        binding.executePendingBindings()
        if (hotCardsItem.contents.size > 1) binding.hotCardsViewpager.setCurrentItem(2, false)
    }

    fun handleMessage(msg: Message?) {
        if (msg?.what != MSG_START_SCROLL) return

        if (this.itemView.isShown) {
            val nextPosition = binding.hotCardsViewpager.currentItem + 1
            targetPosition = if (contentSize <= targetPosition) 0 else nextPosition
            binding.hotCardsViewpager.smoothScrollToPosition(
                item = targetPosition,
                duration = rollingIntervalMs,
                pagePxWidth = this.itemView.width/2
            )
        }
    }

    private class HotCardsContainerViewHandler(viewHolder: HotCardsContainerViewHolder) : Handler(Looper.getMainLooper()) {
        private val holder: WeakReference<HotCardsContainerViewHolder> = WeakReference(viewHolder)
        override fun handleMessage(msg: Message) {
            val viewHolder = holder.get()
            viewHolder?.handleMessage(msg)
        }
    }

    companion object {
        private const val MSG_START_SCROLL = 0x0001
    }
}

internal object HotCardsDiffCallback : DiffUtil.ItemCallback<HotCards.Contents>() {
    override fun areItemsTheSame(oldItem: HotCards.Contents, newItem: HotCards.Contents) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: HotCards.Contents, newItem: HotCards.Contents) =
        oldItem == newItem
}
