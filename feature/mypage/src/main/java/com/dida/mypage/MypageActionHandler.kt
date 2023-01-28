package com.dida.mypage

interface MypageActionHandler {
    fun onWalletClicked()
    fun onLogoutClicked()
    fun onMypageNftTypeClicked(type : MyPageViewModel.MypageNftType)
    fun onSettingsClicked()
}
