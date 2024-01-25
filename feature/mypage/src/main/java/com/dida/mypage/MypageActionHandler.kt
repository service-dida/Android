package com.dida.mypage

import com.dida.domain.main.model.Sort

interface MypageActionHandler {
    fun onWalletClicked()
    fun onMypageNftTypeClicked(type : Sort)
    fun onSettingsClicked()
    fun onUserFollowedClicked()
    fun onUserFollowingClicked()
}
