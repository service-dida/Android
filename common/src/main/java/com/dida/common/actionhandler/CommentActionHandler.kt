package com.dida.common.actionhandler

interface CommentActionHandler {
    fun onCommentUserProfileClicked(userId: Long)
    fun onReportClicked(userId: Long)
    fun onBlockClicked(userId: Long)
    fun onDeleteClicked(commentId: Long)
    fun onUpdateClicked(commentId: Long)
}
