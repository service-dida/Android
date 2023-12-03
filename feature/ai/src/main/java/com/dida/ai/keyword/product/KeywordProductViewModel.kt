package com.dida.ai.keyword.product

import com.dida.ai.keyword.Keyword
import com.dida.ai.keyword.KeywordViewModelDelegate
import com.dida.ai.keyword.toKeyword
import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.local.GetKeywordThingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordProductViewModel @Inject constructor(
    keywordViewModelDelegate: KeywordViewModelDelegate,
    val getKeywordThingsUseCase: GetKeywordThingsUseCase
) : BaseViewModel(),
    KeywordViewModelDelegate by keywordViewModelDelegate
{
    private val TAG = "KeywordProductViewModel"

    private val _keywordsState: MutableStateFlow<List<Keyword.Default>> = MutableStateFlow(emptyList())
    val keywordsState: StateFlow<List<Keyword.Default>> = _keywordsState.asStateFlow()

    init {
        getKeywordThings()
    }

    private fun getKeywordThings() {
        baseViewModelScope.launch {
            getKeywordThingsUseCase()
                .onSuccess { keywords ->
                    _keywordsState.value = keywords.map { it.toKeyword() }
                }.onError { e -> catchError(e) }
        }
    }
}
