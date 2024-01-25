package com.dida.community_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.community_detail.R
import com.dida.community_detail.databinding.HolderCommentEmptyBinding

class CommentEmptyAdapter: ListAdapter<CommentEmptyItem, CommentEmptyAdapter.ViewHolder>(CommentEmptyDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderCommentEmptyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_comment_empty,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind() }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    class ViewHolder(private val binding: HolderCommentEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.executePendingBindings()
        }
    }

    internal object CommentEmptyDiffCallback : DiffUtil.ItemCallback<CommentEmptyItem>() {
        override fun areItemsTheSame(oldItem: CommentEmptyItem, newItem: CommentEmptyItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CommentEmptyItem, newItem: CommentEmptyItem) =
            oldItem.equals(newItem)
    }
}

object CommentEmptyItem




