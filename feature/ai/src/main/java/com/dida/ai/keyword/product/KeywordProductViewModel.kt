package com.dida.ai.keyword.product

import com.dida.ai.keyword.KeywordActionHandler
import com.dida.ai.keyword.KeywordNavigationAction
import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordProductViewModel @Inject constructor(
) : BaseViewModel(), KeywordActionHandler {

    private val TAG = "KeywordProductViewModel"

    private val _nextState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val nextState: StateFlow<Boolean> = _nextState.asStateFlow()

    private val _selectKeywordState: MutableStateFlow<String> = MutableStateFlow("")
    val selectKeywordState: StateFlow<String> = _selectKeywordState.asStateFlow()

    private val _navigationAction: MutableSharedFlow<KeywordNavigationAction> = MutableSharedFlow()
    val navigationAction: SharedFlow<KeywordNavigationAction> = _navigationAction.asSharedFlow()

    override fun onSkipClicked() {
        baseViewModelScope.launch {
            _navigationAction.emit(KeywordNavigationAction.NavigateToSkip)
        }
    }

    override fun onNextClicked() {
        baseViewModelScope.launch {
            _navigationAction.emit(KeywordNavigationAction.NavigateToNext(selectKeywordState.value))
        }
    }

    override fun onKeywordClicked(keyword: String) {
        baseViewModelScope.launch {
            _nextState.value = true
            _selectKeywordState.value = keyword
        }
    }
}
