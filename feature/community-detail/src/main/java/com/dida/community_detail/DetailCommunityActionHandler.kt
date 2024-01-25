package com.dida.community_detail

interface DetailCommunityActionHandler {
    fun onCommentClicked(postId: Long)
    fun onUserProfileClicked(userId: Long)
    fun onCardClicked(cardId: Long)
    fun onDeletePostDialog(postId: Long)
    fun onUpdatePost(postId: Long)
    fun onPostReport(postId: Long)
    fun onPostBlockClicked(postId: Long)
}
