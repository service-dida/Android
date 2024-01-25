package com.dida.update.profile

sealed class UpdateProfileNavigationAction {
    object NavigateToBack: UpdateProfileNavigationAction()
    object NavigateToUpdateProfileImage: UpdateProfileNavigationAction()
}
