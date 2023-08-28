package com.dida.mypage

interface MypageActionHandler {
    fun onWalletClicked()
    fun onMypageNftTypeClicked(type : MyPageViewModel.CardSortType)
    fun onSettingsClicked()
    fun onUserFollowedClicked()
    fun onUserFollowingClicked()
}
