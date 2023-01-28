package com.dida.settings

sealed class SettingsNavigationAction {
    object NavigateToProfileEdit: SettingsNavigationAction()
    object NavigateToPrePassword: SettingsNavigationAction()
    object NavigateToPasswordEdit: SettingsNavigationAction()
    object NavigateToAccountInformation: SettingsNavigationAction()
    object NavigateToNotification: SettingsNavigationAction()
    object NavigateToInVisible: SettingsNavigationAction()
    object NavigateToLogout: SettingsNavigationAction()
}
