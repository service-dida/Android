package com.dida.ai.keyword.result.dialog

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
class AiPictureRestartViewModel @Inject constructor() : BaseViewModel() {

    private val TAG = "AiPictureRestartViewModel"

    private val _selected: MutableStateFlow<RestartMenu> = MutableStateFlow<RestartMenu>(RestartMenu.INIT)
    val selected: StateFlow<RestartMenu> = _selected.asStateFlow()

    private val _navigationAction: MutableSharedFlow<RestartMenu> = MutableSharedFlow<RestartMenu>()
    val navigationAction: SharedFlow<RestartMenu> = _navigationAction.asSharedFlow()

    fun onMenuClicked(restartMenu: RestartMenu) {
        baseViewModelScope.launch {
            _selected.value = restartMenu
        }
    }

    fun onNextButtonClicked() {
        baseViewModelScope.launch {
            _navigationAction.emit(selected.value)
        }
    }
}

enum class RestartMenu {
    INIT, SAME_KEYWORD, RESTART_KEYWORD
}
