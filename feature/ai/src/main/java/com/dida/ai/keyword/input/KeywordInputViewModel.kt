package com.dida.ai.keyword.input

import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordInputViewModel @Inject constructor(
) : BaseViewModel() {
    private val TAG = "KeywordInputViewModel"

    val userInput: MutableStateFlow<String> = MutableStateFlow<String>("")

    private val _drawEnable: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val drawEnable: StateFlow<Boolean> = _drawEnable
        .combine(userInput) { _, input ->
            input.length >= 10
        }.stateIn(
            scope = baseViewModelScope,
            started = SharingStarted.WhileSubscribed(1000),
            initialValue = false
        )

    private val _navigationAction: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val navigationAction: SharedFlow<Unit> = _navigationAction.asSharedFlow()

    fun onDrawClicked() {
        baseViewModelScope.launch {
            _navigationAction.emit(Unit)
        }
    }
}
