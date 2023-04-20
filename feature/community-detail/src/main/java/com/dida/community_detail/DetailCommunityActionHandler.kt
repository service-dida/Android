package com.dida.community_detail

interface DetailCommunityActionHandler {
    fun onCommentClicked(postId: Long)
    fun onCommunityMoreClicked(userId: Long, postId: Long)
    fun onUserProfileClicked(userId: Long)
    fun onCardClicked(cardId: Long)
}
