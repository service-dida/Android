package com.dida.login

sealed class LoginNavigationAction {
    class NavigateToNickname(val email: String): LoginNavigationAction()
    object NavigateToHome: LoginNavigationAction()
    object NavigateToLogin: LoginNavigationAction()
}
