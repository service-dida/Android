package com.dida.common.actionhandler

interface CommunityActionHandler {
    fun onCommunityItemClicked(postId: Long)
    fun onClipOrMoreClicked(postId: Long)
    fun onReportClicked(userId: Long)
    fun onBlockClicked(userId: Long)
    fun onUpdateClicked(postId: Long)
    fun onDeleteClicked(postId: Long)
}
