package com.dida.community_detail

interface DetailCommunityActionHandler {
    fun onCommentClicked()
    fun onCommunityMoreClicked(userId: Long)
    fun onUserProfileClicked()
    fun onCardClicked()
}
