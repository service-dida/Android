package com.dida.mypage

interface MypageActionHandler {
    fun onWalletClicked()
    fun onLogoutClicked()
    fun onUpdateProfileClicked()
    fun onMypageNftTypeClicked(type : MyPageViewModel.MypageNftType)
}