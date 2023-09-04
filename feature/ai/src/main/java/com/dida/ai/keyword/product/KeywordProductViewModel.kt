package com.dida.ai.keyword.product

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

    private val _keywordsState: MutableStateFlow<List<String>> = MutableStateFlow(
        listOf("blakcberry", "bacon pizza", "flower", "guitar", "computer", "chair", "TV", "dictionary", "cell phone")
    )
    val keywordsState: StateFlow<List<String>> = _keywordsState.asStateFlow()
}
