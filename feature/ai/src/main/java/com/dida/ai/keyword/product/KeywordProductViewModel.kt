package com.dida.ai.keyword.product

import com.dida.ai.keyword.Keyword
import com.dida.ai.keyword.KeywordViewModelDelegate
import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class KeywordProductViewModel @Inject constructor(
    keywordViewModelDelegate: KeywordViewModelDelegate
) : BaseViewModel(),
    KeywordViewModelDelegate by keywordViewModelDelegate
{

    private val TAG = "KeywordProductViewModel"

    private val _keywordsState: MutableStateFlow<List<Keyword.Default>> = MutableStateFlow(
        listOf(
            Keyword.Default(word = "blakcberry"),
            Keyword.Default(word = "bacon pizza"),
            Keyword.Default(word = "flower"),
            Keyword.Default(word = "guitar"),
            Keyword.Default(word = "computer"),
            Keyword.Default(word = "chair"),
            Keyword.Default(word = "dictionary"),
            Keyword.Default(word = "cell phone"),
        )
    )
    val keywordsState: StateFlow<List<Keyword.Default>> = _keywordsState.asStateFlow()
}
