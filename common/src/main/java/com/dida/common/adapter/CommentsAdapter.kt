package com.dida.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.R
import com.dida.common.actionhandler.CommentActionHandler
import com.dida.common.databinding.HolderCommentsBinding
import com.dida.domain.model.nav.post.Comments

class CommentsAdapter(
    private val eventListener: CommentActionHandler
) : ListAdapter<Comments, CommentsAdapter.ViewHolder>(CommentsDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderCommentsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_comments,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comments) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object CommentsDiffCallback : DiffUtil.ItemCallback<Comments>() {
        override fun areItemsTheSame(oldItem: Comments, newItem: Comments) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Comments, newItem: Comments) =
            oldItem.equals(newItem)
    }
}
