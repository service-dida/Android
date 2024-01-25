package com.dida.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.R
import com.dida.common.actionhandler.CommentActionHandler
import com.dida.common.ballon.showEditCommentBalloon
import com.dida.common.ballon.showReportCommentBalloon
import com.dida.common.bindingadapters.setOnSingleClickListener
import com.dida.common.databinding.HolderCommentsBinding
import com.dida.domain.main.model.Comment

// FIXME : 댓글 수정하기 화면 필요
class CommentsAdapter(
    private val eventListener: CommentActionHandler
) : ListAdapter<Comment, CommentsAdapter.ViewHolder>(CommentsDiffCallback) {
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
        return ViewHolder(viewDataBinding, eventListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long = getItem(position).commentInfo.commentId * -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_comments

    class ViewHolder(
        private val binding: HolderCommentsBinding,
        private val eventListener: CommentActionHandler
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment) {
            binding.holderModel = item
            binding.moreBtn.isVisible = item.type != "NEED LOGIN"
            binding.moreBtn.setOnSingleClickListener {
                if (item.type == "MINE") {
                    eventListener.onDeleteClicked(item.commentInfo.commentId)
                } else {
                    it.showReportCommentBalloon(userId = item.memberInfo.memberId, listener = eventListener)
                }
            }
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
