package com.dida.temp_password

sealed class TempPasswordNavigationAction {
    object NavigateToPasswordChange : TempPasswordNavigationAction()
}
