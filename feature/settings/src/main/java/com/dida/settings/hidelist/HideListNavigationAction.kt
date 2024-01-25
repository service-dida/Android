package com.dida.settings.hidelist

sealed class HideListNavigationAction {
    class NavigateToDetailNft(val cardId: Long): HideListNavigationAction()
}
