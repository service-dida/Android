package com.dida.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.R
import com.dida.common.actionhandler.CommunityActionHandler
import com.dida.common.databinding.HolderCommunityBinding
import com.dida.domain.model.main.Posts

class CommunityPagingAdapter(
    private val eventListener: CommunityActionHandler
) : PagingDataAdapter<Posts, CommunityPagingAdapter.ViewHolder>(CommuityDiffCallback) {

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

    class ViewHolder(private val binding: HolderCommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val adapter = PostCommentsAdapter()

        fun bind(item: Posts) {
            binding.holderModel = item
            adapter.submitList(item.commentList)
            binding.commentRecycler.adapter = adapter
            binding.executePendingBindings()
        }
    }

    internal object CommuityDiffCallback : DiffUtil.ItemCallback<Posts>() {
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts) =
            oldItem.postId == newItem.postId

        override fun areContentsTheSame(oldItem: Posts, newItem: Posts) =
            oldItem.equals(newItem)
    }
}
