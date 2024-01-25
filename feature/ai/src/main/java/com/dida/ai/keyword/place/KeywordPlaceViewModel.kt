package com.dida.ai.keyword.place

import com.dida.ai.keyword.Keyword
import com.dida.ai.keyword.KeywordViewModelDelegate
import com.dida.ai.keyword.toKeyword
import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.local.GetKeywordPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordPlaceViewModel @Inject constructor(
    keywordViewModelDelegate: KeywordViewModelDelegate,
    val keywordPlacesUseCase: GetKeywordPlacesUseCase
) : BaseViewModel(),
    KeywordViewModelDelegate by keywordViewModelDelegate
{
    private val TAG = "KeywordViewModel"

    private val _keywordsState: MutableStateFlow<List<Keyword.Default>> = MutableStateFlow(emptyList())
    val keywordsState: StateFlow<List<Keyword.Default>> = _keywordsState.asStateFlow()

    init {
        getKeywordPlaces()
    }

    fun getKeywordPlaces() {
        baseViewModelScope.launch {
            keywordPlacesUseCase()
                .onSuccess { keywords ->
                    _keywordsState.value = keywords.map { it.toKeyword() }
                }.onError { e -> catchError(e) }
        }
    }

}
