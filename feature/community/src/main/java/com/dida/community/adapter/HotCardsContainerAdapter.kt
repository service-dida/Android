package com.dida.community.adapter

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
import com.dida.community.HotCardActionHandler
import com.dida.community.databinding.HolderHotCardsContainerBinding
import com.dida.domain.model.main.HotCards
import com.dida.domain.model.main.HotItems
import java.lang.ref.WeakReference

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
                hotCardsRecyclerView.addItemDecoration(
                    CirclePagerIndicatorDecoration(
                        isInfiniteScroll = true,
                        activeColor = activeColor,
                        inactiveColor = inactiveColor,
                        indicatorHeight = indicatorHeight
                    )
                )
                PagerSnapHelper().apply { attachToRecyclerView(hotCardsRecyclerView) }
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

    private var contentSize = 0
    private val repeatIntervalMs = 2850L
    private val rollingIntervalMs = 150L
    private var targetPosition = 0

    private val handler = HotCardsContainerViewHandler(this)
    private val width = context.resources.getDimensionPixelSize(com.dida.common.R.dimen.hot_card_item_width)
    private val interval = context.resources.getDimensionPixelSize(com.dida.common.R.dimen.hot_card_item_interval)

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
        binding.hotCardsRecyclerView.addOnChildAttachStateChangeListener(childAttachStateChangeListener)
        binding.hotCardsRecyclerView.addOnScrollListener(scrollListener)
    }

    fun bind(hotCardsItem: HotCards.Contents) {
        if (hotCardsItem.contents.size > 1) {
            contentSize = hotCardsItem.contents.size + 2
            binding.holderModel = HotCards.Contents(listOf(hotCardsItem.contents.last()) + hotCardsItem.contents + hotCardsItem.contents.first())
            binding.hotCardsRecyclerView.scrollBy(width = width)
        } else {
            contentSize = hotCardsItem.contents.size
            binding.holderModel = hotCardsItem
            binding.hotCardsRecyclerView.removeOnChildAttachStateChangeListener(childAttachStateChangeListener)
            binding.hotCardsRecyclerView.removeOnScrollListener(scrollListener)
        }
        binding.executePendingBindings()
    }

    fun handleMessage(msg: Message?) {
        if (msg?.what != MSG_START_SCROLL) return

        if (this.itemView.isShown) {
            val visibleItem = (binding.hotCardsRecyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            targetPosition = if (contentSize <= targetPosition) 0 else visibleItem+1
            val scroller = if (targetPosition == 0) endToStartSmoothScroller else smoothScroller
            scroller.targetPosition = targetPosition
            if (!(targetPosition == contentSize || targetPosition == 0)) binding.hotCardsRecyclerView.layoutManager?.startSmoothScroll(scroller)
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
