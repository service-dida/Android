package com.dida.create_community

interface CreateCommunityActionHandler {
    fun onNftSelectClicked(cardId: Long)
    fun onLikeButtonClicked()
    fun onCreateButtonClicked()
}
