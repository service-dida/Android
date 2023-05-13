package com.dida.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.R
import com.dida.common.actionhandler.CommunityActionHandler
import com.dida.common.ballon.showReportPostBalloon
import com.dida.common.bindingadapters.setOnSingleClickListener
import com.dida.common.databinding.HolderCommunityBinding
import com.dida.domain.model.main.PostType
import com.dida.domain.model.main.Posts

class CommunityAdapter(
    private val eventListener: CommunityActionHandler
) : ListAdapter<Posts, CommunityAdapter.ViewHolder>(CommuityDiffCallback) {

    init {
        setHasStableIds(true)
    }

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

    override fun getItemId(position: Int): Long = getItem(position).postId * -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_community

    class ViewHolder(private val binding: HolderCommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val adapter = PostCommentsAdapter()

        fun bind(item: Posts) {
            binding.holderModel = item
            if (item.commentList.size > 2) adapter.submitList(item.commentList.slice(0 until 2))
            else adapter.submitList(item.commentList)
            binding.commentRecycler.adapter = adapter
            binding.executePendingBindings()
        }
    }

    internal object CommuityDiffCallback : DiffUtil.ItemCallback<Posts>() {
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts) =
            oldItem.postId == newItem.postId

        override fun areContentsTheSame(oldItem: Posts, newItem: Posts) =
            oldItem == newItem
    }
}
