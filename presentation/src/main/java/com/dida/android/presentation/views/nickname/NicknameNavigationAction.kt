package com.dida.android.presentation.views.nickname

sealed class NicknameNavigationAction {
    object NavigateToNickNameFail: NicknameNavigationAction()
    object NavigateToHome: NicknameNavigationAction()
}