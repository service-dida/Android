package com.dida.android.presentation.views.login

sealed class LoginNavigationAction {
    object NavigateToLoginFail: LoginNavigationAction()
    class NavigateToNickname(val email: String): LoginNavigationAction()
    object NavigateToHome: LoginNavigationAction()
}