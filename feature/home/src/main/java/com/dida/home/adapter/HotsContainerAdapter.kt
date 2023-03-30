package com.dida.home.adapter

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.dida.common.util.context
import com.dida.common.util.scrollBy
import com.dida.common.widget.CirclePagerIndicatorDecoration
import com.dida.domain.model.main.HotItems
import com.dida.home.HomeActionHandler
import com.dida.home.databinding.HolderHotsContainerBinding
import java.lang.ref.WeakReference


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
                    val activeColor: Int = ContextCompat.getColor(parent.context, com.dida.common.R.color.brand_lemon)
                    val inactiveColor: Int = ContextCompat.getColor(parent.context, com.dida.common.R.color.surface6)
                    hotsRecyclerView.addItemDecoration(
                        CirclePagerIndicatorDecoration(
                            isInfiniteScroll = true,
                            activeColor = activeColor,
                            inactiveColor = inactiveColor,
                            indicatorHeight = indicatorHeight
                        )
                    )
                    PagerSnapHelper().apply { attachToRecyclerView(hotsRecyclerView) }
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

    private val repeatIntervalMs = 2850L
    private val rollingIntervalMs = 150L

    private var targetPosition = 0
    private var contentSize = 0

    private val handler = HotsContainerViewHandler(this)
    private val width = context.resources.getDimensionPixelSize(com.dida.common.R.dimen.hot_item_width)
    private val interval = context.resources.getDimensionPixelSize(com.dida.common.R.dimen.hot_item_interval)

    private val smoothScroller
        get() = object : LinearSmoothScroller(context) {
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float =
                rollingIntervalMs / displayMetrics.densityDpi.toFloat()
        }

    private val endToStartSmoothScroller
        get() = object : LinearSmoothScroller(context) {
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float =
                (rollingIntervalMs / contentSize) / displayMetrics.densityDpi.toFloat()
        }

    private val childAttachStateChangeListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            handler.sendEmptyMessageDelayed(MSG_START_SCROLL, repeatIntervalMs)
        }

        override fun onChildViewDetachedFromWindow(view: View) {
            if (view.isShown) {
                handler.removeMessages(MSG_START_SCROLL)
            }
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollHorizontally(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(contentSize - 2, 0)
            } else if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(1, interval)
            }
        }
    }

    init {
        binding.hotsRecyclerView.addOnChildAttachStateChangeListener(childAttachStateChangeListener)
        binding.hotsRecyclerView.addOnScrollListener(scrollListener)
    }

    fun bind(hotItems: HotItems.Contents) {
        if (hotItems.contents.size > 1) {
            contentSize = hotItems.contents.size + 2
            binding.holderModel = HotItems.Contents(listOf(hotItems.contents.last()) + hotItems.contents + hotItems.contents.first())
            binding.hotsRecyclerView.scrollBy(width = width)
        } else {
            contentSize = hotItems.contents.size
            binding.holderModel = hotItems
            binding.hotsRecyclerView.removeOnChildAttachStateChangeListener(childAttachStateChangeListener)
            binding.hotsRecyclerView.removeOnScrollListener(scrollListener)
        }
        binding.executePendingBindings()
    }

    fun handleMessage(msg: Message?) {
        if (msg?.what != MSG_START_SCROLL) return

        if (this.itemView.isShown) {
            val visibleItem = (binding.hotsRecyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            targetPosition = if (contentSize <= targetPosition) 0 else visibleItem+1
            val scroller = if (targetPosition == 0) endToStartSmoothScroller else smoothScroller
            scroller.targetPosition = targetPosition
            if (!(targetPosition == contentSize || targetPosition == 0)) binding.hotsRecyclerView.layoutManager?.startSmoothScroll(scroller)
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
