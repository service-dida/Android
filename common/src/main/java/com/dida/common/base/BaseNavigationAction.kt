package com.dida.common.base

sealed class BaseNavigationAction {
    object NavigateToDuplicateLogin : BaseNavigationAction()
    object NavigateToLogin : BaseNavigationAction()
}
