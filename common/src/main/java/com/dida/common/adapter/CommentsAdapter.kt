package com.dida.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.R
import com.dida.common.actionhandler.CommentActionHandler
import com.dida.common.ballon.showReportCommentBalloon
import com.dida.common.ballon.showEditCommentBalloon
import com.dida.common.bindingadapters.setOnSingleClickListener
import com.dida.common.databinding.HolderCommentsBinding
import com.dida.domain.model.main.Comments

class CommentsAdapter(
    private val eventListener: CommentActionHandler
) : ListAdapter<Comments, CommentsAdapter.ViewHolder>(CommentsDiffCallback) {
    init {
        setHasStableIds(true)
    }

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

    override fun getItemId(position: Int): Long = getItem(position).commentId.toLong() * -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_comments

    class ViewHolder(private val binding: HolderCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comments) {
            binding.holderModel = item
            binding.moreBtn.setOnSingleClickListener {
                if (item.type == "MINE") it.showEditCommentBalloon(commentId = item.commentId, listener = binding.eventListener!!)
                else it.showReportCommentBalloon(userId = item.userId, listener = binding.eventListener!!)
            }
            binding.executePendingBindings()
        }
    }

    internal object CommentsDiffCallback : DiffUtil.ItemCallback<Comments>() {
        override fun areItemsTheSame(oldItem: Comments, newItem: Comments) =
            oldItem.commentId == newItem.commentId

        override fun areContentsTheSame(oldItem: Comments, newItem: Comments) =
            oldItem == newItem
    }
}
