package com.dida.ai.keyword.result

sealed class KeywordResultNavigationAction {
    object NavigateToRestartKeyword: KeywordResultNavigationAction()
    object NavigateToDownloadAiPicture: KeywordResultNavigationAction()
    class NavigateToCreateNft(val imageUrl: String): KeywordResultNavigationAction()
    object NavigateToNotMatchedPicture: KeywordResultNavigationAction()
    object NavigateToServiceDenied: KeywordResultNavigationAction()
}
