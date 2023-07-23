package com.dida.user_profile

interface UserProfileActionHandler {
    fun onFollowClicked()
    fun onCardSortTypeClicked(type : UserProfileViewModel.CardSortType)
    fun onUserFollowerClicked()
    fun onUserFollowingClicked()
}
