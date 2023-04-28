package com.dida.common.actionhandler

interface CommunityActionHandler {
    fun onCommunityItemClicked(postId: Long)
    fun onReportClicked(postId: Long)
    fun onBlockClicked(postId: Long)
    fun onUpdateClicked(postId: Long)
    fun onDeleteClicked(postId: Long)
}
