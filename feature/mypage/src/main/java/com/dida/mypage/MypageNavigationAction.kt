package com.dida.mypage

sealed class MypageNavigationAction {
    object NavigateToSettings: MypageNavigationAction()
    object NavigateToEmail: MypageNavigationAction()
    object NavigateToWallet : MypageNavigationAction()
    class NavigateToDetailNft(val cardId: Long) : MypageNavigationAction()
    object NavigateToCreate: MypageNavigationAction()
}
