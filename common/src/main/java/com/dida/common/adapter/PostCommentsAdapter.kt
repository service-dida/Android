package com.dida.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.R
import com.dida.common.databinding.HolderPostCommentsBinding
import com.dida.domain.main.model.Comment

class PostCommentsAdapter(
) : ListAdapter<Comment, PostCommentsAdapter.ViewHolder>(CommentsDiffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderPostCommentsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_post_comments,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long = getItem(position).commentInfo.commentId * -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_post_comments

    class ViewHolder(private val binding: HolderPostCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object CommentsDiffCallback : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment) =
            oldItem.commentInfo.commentId == newItem.commentInfo.commentId

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment) =
            oldItem == newItem
    }
}
