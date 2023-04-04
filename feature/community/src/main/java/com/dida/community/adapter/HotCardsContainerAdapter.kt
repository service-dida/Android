package com.dida.community.adapter

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.dida.common.util.context
import com.dida.common.util.scrollBy
import com.dida.community.HotCardActionHandler
import com.dida.community.databinding.HolderHotCardsContainerBinding
import com.dida.domain.model.main.HotCards
import java.lang.ref.WeakReference


class HotCardsContainerAdapter(
    private val eventListener: HotCardActionHandler
) : ListAdapter<HotCards.Contents, HotsContainerViewHolder>(HotCardsDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotsContainerViewHolder {
        return HotsContainerViewHolder(
            HolderHotCardsContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@HotCardsContainerAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: HotsContainerViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1
}

class HotsContainerViewHolder(
    private val binding: HolderHotCardsContainerBinding
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
        binding.hotCardsRecyclerView.addOnChildAttachStateChangeListener(childAttachStateChangeListener)
        binding.hotCardsRecyclerView.addOnScrollListener(scrollListener)
    }

    fun bind(hotCards: HotCards.Contents) {
        if (hotCards.contents.size > 1) {
            contentSize = hotCards.contents.size + 2
            binding.holderModel = HotCards.Contents(listOf(hotCards.contents.last()) + hotCards.contents + hotCards.contents.first())
            binding.hotCardsRecyclerView.scrollBy(width = width)
        } else {
            contentSize = hotCards.contents.size
            binding.holderModel = hotCards
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

internal object HotCardsDiffCallback : DiffUtil.ItemCallback<HotCards.Contents>() {
    override fun areItemsTheSame(oldItem: HotCards.Contents, newItem: HotCards.Contents) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: HotCards.Contents, newItem: HotCards.Contents) =
        oldItem == newItem
}
