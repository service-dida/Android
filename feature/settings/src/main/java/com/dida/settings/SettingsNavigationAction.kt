package com.dida.settings

sealed class SettingsNavigationAction {
    object NavigateToProfileEdit: SettingsNavigationAction()
    object NavigateToPasswordEdit: SettingsNavigationAction()
    object NavigateToAccountInformation: SettingsNavigationAction()
    object NavigateToNotification: SettingsNavigationAction()
    object NavigateToHideList: SettingsNavigationAction()
    object NavigateToLogout: SettingsNavigationAction()
    object NavigateToLogoutComplete: SettingsNavigationAction()
}
