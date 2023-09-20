package com.dida.user_profile

import com.dida.domain.main.model.Sort

interface UserProfileActionHandler {
    fun onFollowClicked()
    fun onCardSortTypeClicked(type : Sort)
    fun onUserFollowerClicked()
    fun onUserFollowingClicked()
}
