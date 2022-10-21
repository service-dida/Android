package com.dida.android.presentation.views.createcommunity

sealed class CreateCommunityNavigationAction {
    class NavigateToSelectNft(val nftId: Int): CreateCommunityNavigationAction()
}