package com.dida.mypage

interface MypageActionHandler {
    fun onWalletClicked()
    fun onMypageNftTypeClicked(type : MyPageViewModel.MypageNftType)
    fun onSettingsClicked()
}
