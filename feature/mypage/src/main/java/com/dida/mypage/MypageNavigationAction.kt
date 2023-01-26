package com.dida.mypage

sealed class MypageNavigationAction {
    object NavigateToInit: MypageNavigationAction()
    object NavigateToEmail: MypageNavigationAction()
    object NavigateToWallet : MypageNavigationAction()
    object NavigateToHome: MypageNavigationAction()
    class NavigateToDetailNft(val cardId: Long) : MypageNavigationAction()
    class NavigateToUpdateProfile(val image :String, val nickname : String, val description : String) : MypageNavigationAction()
}