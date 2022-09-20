package com.dida.android.presentation.views.nav.mypage

sealed class MypageNavigationAction {
    object NavigateToInit: MypageNavigationAction()
    object NavigateToEmail: MypageNavigationAction()
    object NavigateToWallet : MypageNavigationAction()
    object NavigateToHome: MypageNavigationAction()
    object NavigateToDetailNft : MypageNavigationAction()
}