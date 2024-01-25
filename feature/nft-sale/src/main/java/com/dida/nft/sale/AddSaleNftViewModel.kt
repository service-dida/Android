package com.dida.nft.sale

import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSaleNftViewModel @Inject constructor(
) : BaseViewModel(), AddSaleNftActionHandler {

    private val TAG = "AddNftPriceViewModel"

    private val _navigationEvent: MutableSharedFlow<AddSaleNftNavigationAction> = MutableSharedFlow<AddSaleNftNavigationAction>()
    val navigationEvent: SharedFlow<AddSaleNftNavigationAction> = _navigationEvent

    private val _okBtnState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val okBtnState: StateFlow<Boolean> = _okBtnState

    override fun onOkBtnClicked() {
        if(_okBtnState.value) {
            baseViewModelScope.launch {
                _navigationEvent.emit(AddSaleNftNavigationAction.NavigateToDismiss)
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
