package com.dida.ai.keyword

import com.dida.common.di.ApplicationScope
import com.dida.common.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface KeywordViewModelDelegate {

    val nextState: StateFlow<Boolean>
    val selectKeywordState: StateFlow<String>
    val navigationAction: SharedFlow<KeywordNavigationAction>

    fun onSkipClicked()
    fun onNextClicked()
    fun onKeywordClicked(keyword: String)
}

class DefaultKeywordViewModelDelegate @Inject constructor(
    @ApplicationScope private val externalScope: CoroutineScope,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
): KeywordViewModelDelegate {

    private val _nextState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val nextState: StateFlow<Boolean> = _nextState.asStateFlow()

    private val _selectKeywordState: MutableStateFlow<String> = MutableStateFlow("")
    override val selectKeywordState: StateFlow<String> = _selectKeywordState.asStateFlow()

    private val _navigationAction: MutableSharedFlow<KeywordNavigationAction> = MutableSharedFlow()
    override val navigationAction: SharedFlow<KeywordNavigationAction> = _navigationAction.asSharedFlow()

    override fun onSkipClicked() {
        externalScope.launch(mainDispatcher) {
            _navigationAction.emit(KeywordNavigationAction.NavigateToSkip)
        }
    }

    override fun onNextClicked() {
        externalScope.launch(mainDispatcher) {
            _navigationAction.emit(KeywordNavigationAction.NavigateToNext)
        }
    }

    override fun onKeywordClicked(keyword: String) {
        externalScope.launch(mainDispatcher) {
            _nextState.value = true
            _selectKeywordState.value = keyword
        }
    }
}
