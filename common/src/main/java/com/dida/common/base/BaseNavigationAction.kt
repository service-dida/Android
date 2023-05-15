package com.dida.common.base

sealed class BaseNavigationAction {
    object NavigateToHome : BaseNavigationAction()
    object NavigateToLogin : BaseNavigationAction()
}
