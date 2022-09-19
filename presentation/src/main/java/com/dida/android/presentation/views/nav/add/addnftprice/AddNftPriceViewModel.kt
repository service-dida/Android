package com.dida.android.presentation.views.nav.add.addnftprice

import com.dida.android.presentation.base.BaseViewModel
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

    private val _okBtnState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val okBtnState: StateFlow<Boolean> = _okBtnState

    override fun onOkBtnClicked() {
        if(_okBtnState.value) {
            baseViewModelScope.launch {
                _navigationEvent.emit(AddNftPriceNavigationAction.NavigateToDismiss)
            }
        }
    }

    val userInputStateFlow: MutableStateFlow<String> = MutableStateFlow<String>("")

    init {
        baseViewModelScope.launch {
            userInputStateFlow.debounce(100).collect {
                if(it.isEmpty()) { _okBtnState.value = false }
                else { _okBtnState.value = true }
            }
        }
    }
}