package com.dida.ai.keyword.result

sealed class KeywordResultNavigationAction {
    object NavigateToRestartKeyword: KeywordResultNavigationAction()
    object NavigateToDownloadAiPicture: KeywordResultNavigationAction()
    object NavigateToCreateNftDialog: KeywordResultNavigationAction()
}
