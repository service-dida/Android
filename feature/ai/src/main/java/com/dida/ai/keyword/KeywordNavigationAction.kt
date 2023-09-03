package com.dida.ai.keyword

sealed class KeywordNavigationAction {
    class NavigateToNext(val keyword: String): KeywordNavigationAction()
    object NavigateToSkip: KeywordNavigationAction()
}
