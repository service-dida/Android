package com.dida.settings

import com.dida.common.base.BaseViewModel
import com.dida.data.DataApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel() {

    private val TAG = "SettingsViewModel"

    private val _navigateToMainEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val navigateToMainEvent: SharedFlow<Unit> = _navigateToMainEvent

    val settings: StateFlow<List<SETTINGS>> = MutableStateFlow(
        listOf(
            SETTINGS.EDIT_PROFILE, SETTINGS.EDIT_PASSWORD, SETTINGS.ACCOUNT,
            SETTINGS.NOTIFICATION, SETTINGS.INVISIBLE_CARD, SETTINGS.BLOCK_USER,
            SETTINGS.PRIVACY, SETTINGS.SERVICE
        )
    ).asStateFlow()

    fun logOut() {
        baseViewModelScope.launch {
            DataApplication.dataStorePreferences.removeAccountToken()
            _navigateToMainEvent.emit(Unit)
        }
    }
}

enum class SETTINGS {
    EDIT_PROFILE, EDIT_PASSWORD, ACCOUNT, NOTIFICATION, INVISIBLE_CARD, BLOCK_USER, PRIVACY, SERVICE
}
