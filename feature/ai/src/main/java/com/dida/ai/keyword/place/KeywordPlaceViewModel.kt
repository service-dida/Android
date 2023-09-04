package com.dida.ai.keyword.place

import com.dida.ai.keyword.KeywordViewModelDelegate
import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class KeywordPlaceViewModel @Inject constructor(
    keywordViewModelDelegate: KeywordViewModelDelegate
) : BaseViewModel(),
    KeywordViewModelDelegate by keywordViewModelDelegate
{
    private val TAG = "KeywordViewModel"

    private val _keywordsState: MutableStateFlow<List<String>> = MutableStateFlow(
        listOf("sea", "Plane", "Mountain", "River", "Cityscape", "Workplace", "Sky", "Sofa", "Cafe")
    )
    val keywordsState: StateFlow<List<String>> = _keywordsState.asStateFlow()

}
