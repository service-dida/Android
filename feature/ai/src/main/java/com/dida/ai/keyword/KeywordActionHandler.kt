package com.dida.ai.keyword

interface KeywordActionHandler {
    fun onSkipClicked()
    fun onNextClicked()
    fun onKeywordClicked(keyword: String)
}
