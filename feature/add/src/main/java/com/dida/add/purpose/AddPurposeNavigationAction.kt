package com.dida.add.purpose

sealed class AddPurposeNavigationAction {
    object NavigateToSaled: AddPurposeNavigationAction()
    object NavigateToNotSaled : AddPurposeNavigationAction()
    object NavigateToMyPage : AddPurposeNavigationAction()
}