package com.dida.community.adapter

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.community.HotCardActionHandler
import com.dida.community.databinding.HolderHotCardsContainerBinding
import com.dida.domain.model.main.HotCards
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
            HolderHotCardsContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@HotCardsContainerAdapter.eventListener
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

    private val handler = HotCardsContainerViewHandler(this)

    init {
        handler.sendEmptyMessageDelayed(MSG_START_SCROLL, repeatIntervalMs)
    }

    fun bind(hotCards: HotCards.Contents) {
        contentSize = hotCards.contents.size*2
        binding.holderModel = HotCards.Contents(hotCards.contents + hotCards.contents)
        binding.executePendingBindings()
    }

    fun handleMessage(msg: Message?) {
        if (msg?.what != MSG_START_SCROLL) return

        if (this.itemView.isShown) {
            val visibleItem = (binding.hotCardsRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (visibleItem == contentSize / 2 + 1) {
                binding.hotCardsRecyclerView.scrollToPosition(1)
            } else {
                binding.hotCardsRecyclerView.scrollBy(SCROLL_DX, 0)
            }
            handler.sendEmptyMessageDelayed(MSG_START_SCROLL, repeatIntervalMs)
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
        private const val SCROLL_DX = 5
        private const val repeatIntervalMs = 25L
    }
}

internal object HotCardsDiffCallback : DiffUtil.ItemCallback<HotCards.Contents>() {
    override fun areItemsTheSame(oldItem: HotCards.Contents, newItem: HotCards.Contents) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: HotCards.Contents, newItem: HotCards.Contents) =
        oldItem == newItem
}
