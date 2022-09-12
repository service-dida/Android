package com.dida.android.presentation.adapter.detailnft

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.databinding.HolderCommentsBinding
import com.dida.android.databinding.HolderCommunityBinding
import com.dida.domain.model.nav.detailnft.Comments
import com.dida.domain.model.nav.detailnft.Community

class CommentsAdapter() : ListAdapter<Comments, CommentsAdapter.ViewHolder>(CommentsDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderCommentsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_comments,
            parent,
            false
        )
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