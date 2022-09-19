package com.dida.android.presentation.views.nav.add.addnftprice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.views.nav.home.HomeNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNftPriceViewModel @Inject constructor(
) : BaseViewModel(), AddNftPriceActionHandler {

    private val TAG = "AddNftPriceViewModel"

    private val _navigationEvent: MutableSharedFlow<AddNftPriceNavigationAction> = MutableSharedFlow<AddNftPriceNavigationAction>()
    val navigationEvent: SharedFlow<AddNftPriceNavigationAction> = _navigationEvent

    private val _okBtnStateStateFlow: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val okBtnStateStateFlow: StateFlow<Boolean> = _okBtnStateStateFlow

    override fun onOkBtnClicked() {
        if(_okBtnStateStateFlow.value) {
            baseViewModelScope.launch {
                _navigationEvent.emit(AddNftPriceNavigationAction.NavigateToDismiss)
            }
        }
    }

    val userInputStateFlow: MutableStateFlow<String> = MutableStateFlow<String>("")

    init {
        baseViewModelScope.launch {
            userInputStateFlow.debounce(100).collect {
                if(it.isEmpty()) { _okBtnStateStateFlow.value = false }
                else { _okBtnStateStateFlow.value = true }
            }
        }
    }
}