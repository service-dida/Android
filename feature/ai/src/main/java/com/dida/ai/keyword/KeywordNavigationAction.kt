package com.dida.ai.keyword

sealed class KeywordNavigationAction {
    object NavigateToNext: KeywordNavigationAction()
    object NavigateToSkip: KeywordNavigationAction()
}
