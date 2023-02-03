package com.dida.create_community

sealed class CreateCommunityNavigationAction {
    class NavigateToSelectNft(val cardId: Long): CreateCommunityNavigationAction()
}
