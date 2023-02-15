package com.dida.settings

import com.dida.common.base.BaseViewModel
import com.dida.data.DataApplication
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CreateUserAPI
import com.dida.domain.usecase.main.NicknameCheckAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel(), SettingsActionHandler {

    private val TAG = "SettingsViewModel"

    private val _navigationEvent: MutableSharedFlow<SettingsNavigationAction> = MutableSharedFlow<SettingsNavigationAction>()
    val navigationEvent: SharedFlow<SettingsNavigationAction> = _navigationEvent

    override fun onProfileEditClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(SettingsNavigationAction.NavigateToProfileEdit)
        }
    }

    override fun onPrePasswordClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(SettingsNavigationAction.NavigateToPrePassword)
        }
    }

    override fun onPasswordEditClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(SettingsNavigationAction.NavigateToPasswordEdit)
        }
    }

    override fun onAccountInformationClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(SettingsNavigationAction.NavigateToAccountInformation)
        }
    }

    override fun onNotificationClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(SettingsNavigationAction.NavigateToNotification)
        }
    }

    override fun onInVisibleClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(SettingsNavigationAction.NavigateToHideList)
        }
    }

    override fun onLogoutClicked() {
        baseViewModelScope.launch {
            baseViewModelScope.launch {
                DataApplication.dataStorePreferences.removeAccountToken()
                _navigationEvent.emit(SettingsNavigationAction.NavigateToLogout)
            }
        }
    }
}
