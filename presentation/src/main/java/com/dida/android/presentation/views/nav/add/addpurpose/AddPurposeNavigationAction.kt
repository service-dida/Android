package com.dida.android.presentation.views.nav.add.addpurpose

sealed class AddPurposeNavigationAction {
    object NavigateToSaled: AddPurposeNavigationAction()
    object NavigateToNotSaled : AddPurposeNavigationAction()
    object NavigateToMyPage : AddPurposeNavigationAction()
}