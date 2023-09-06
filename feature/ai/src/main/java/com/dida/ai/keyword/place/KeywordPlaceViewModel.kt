package com.dida.ai.keyword.place

import com.dida.ai.keyword.Keyword
import com.dida.ai.keyword.KeywordViewModelDelegate
import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class KeywordPlaceViewModel @Inject constructor(
    keywordViewModelDelegate: KeywordViewModelDelegate
) : BaseViewModel(),
    KeywordViewModelDelegate by keywordViewModelDelegate
{
    private val TAG = "KeywordViewModel"

    private val _keywordsState: MutableStateFlow<List<Keyword.Default>> = MutableStateFlow(
        listOf(
            Keyword.Default(word = "sea"),
            Keyword.Default(word = "Plane"),
            Keyword.Default(word = "Mountain"),
            Keyword.Default(word = "River"),
            Keyword.Default(word = "Cityscape"),
            Keyword.Default(word = "Workplace"),
            Keyword.Default(word = "Sky"),
            Keyword.Default(word = "Sofa"),
            Keyword.Default(word = "Cafe")
        )
    )
    val keywordsState: StateFlow<List<Keyword.Default>> = _keywordsState.asStateFlow()

}
