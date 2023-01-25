package com.dida.nickname

sealed class NicknameNavigationAction {
    object NavigateToNickNameFail: NicknameNavigationAction()
    object NavigateToHome: NicknameNavigationAction()
}
