package com.dida.common.actionhandler

interface CommunityActionHandler {
    fun onCommunityItemClicked(postId: Long)
    fun onClipOrMoreClicked(postId: Long)
}
