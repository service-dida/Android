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

    val settings: StateFlow<List<Settings>> = MutableStateFlow(initSettings).asStateFlow()

    fun logOut() {
        baseViewModelScope.launch {
            DataApplication.dataStorePreferences.removeAccountToken()
            _navigateToMainEvent.emit(Unit)
        }
    }
}

private val initSettings = listOf(
    Settings.EditProfile(), Settings.EditPassword(), Settings.InvisibleCard(),
    Settings.BlockUser(), Settings.Privacy(), Settings.Service()
)


