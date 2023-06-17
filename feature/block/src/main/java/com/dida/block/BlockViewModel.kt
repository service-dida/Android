package com.dida.block

import com.dida.common.base.BaseViewModel
import com.dida.data.DataApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockViewModel @Inject constructor() : BaseViewModel() {

    private val TAG = "BlockViewModel"

    private val _navigateToMainEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val navigateToMainEvent: SharedFlow<Unit> = _navigateToMainEvent

    fun logOut() {
        baseViewModelScope.launch {
            DataApplication.dataStorePreferences.removeAccountToken()
            _navigateToMainEvent.emit(Unit)
        }
    }
}
