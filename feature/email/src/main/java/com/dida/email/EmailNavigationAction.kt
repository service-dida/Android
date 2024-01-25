package com.dida.email

sealed class EmailNavigationAction {
    object SuccessResetPassword: EmailNavigationAction()
    object SuccessCreateWallet: EmailNavigationAction()
}
