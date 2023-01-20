package com.dida.login

sealed class LoginNavigationAction {
    object NavigateToLoginFail: LoginNavigationAction()
    class NavigateToNickname(val email: String): LoginNavigationAction()
    object NavigateToHome: LoginNavigationAction()
    object NavigateToLogin: LoginNavigationAction()
}
