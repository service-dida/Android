package com.dida.create_community_input

sealed class CreateCommunityInputNavigationAction {
    object NavigateToBack: CreateCommunityInputNavigationAction()
    object NavigateToCommunity: CreateCommunityInputNavigationAction()
}
