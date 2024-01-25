package com.dida.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.R
import com.dida.common.actionhandler.CommunityActionHandler
import com.dida.common.ballon.showReportPostBalloon
import com.dida.common.bindingadapters.setOnSingleClickListener
import com.dida.common.databinding.HolderCommunityBinding
import com.dida.domain.main.model.Post

class CommunityPagingAdapter(
    private val eventListener: CommunityActionHandler
) : PagingDataAdapter<Post, CommunityPagingAdapter.ViewHolder>(CommunityDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderCommunityBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_community,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemViewType(position: Int): Int = R.layout.holder_community

    class ViewHolder(private val binding: HolderCommunityBinding) : RecyclerView.ViewHolder(binding.root) {
        private val adapter = PostCommentsAdapter()

        fun bind(item: Post) {
            binding.holderModel = item
            if (item.comments.size > 2) adapter.submitList(item.comments.slice(0 until 2))
            else adapter.submitList(item.comments)
            binding.commentRecycler.adapter = adapter
            binding.moreBtn.isVisible = item.type == "NOT MINE"
            binding.moreBtn.setOnSingleClickListener {
                it.showReportPostBalloon(postId = item.postInfo.postId, listener = binding.eventListener!!)
            }
            binding.executePendingBindings()
        }
    }

    internal object CommunityDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.postInfo.postId == newItem.postInfo.postId

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}
