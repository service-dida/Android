package com.dida.swap.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.SwapDidaToKlayAPI
import com.dida.domain.usecase.main.SwapKlayToDidaAPI
import com.dida.swap.SwapType
import com.dida.swap.SwapViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SwapLoadingViewModel @AssistedInject constructor(
    @Assisted("swapType") val swapType: SwapType,
    @Assisted("password") val password: String,
    @Assisted("amount") val amount: Float,
    private val swapKlayToDidaApi: SwapKlayToDidaAPI,
    private val swapDidaToKlayAPI: SwapDidaToKlayAPI
) : BaseViewModel() {

    private val TAG = "SwapLoadingViewModel"

    private val _navigationEvent: MutableSharedFlow<SwapLoadingNavigationAction> =
        MutableSharedFlow<SwapLoadingNavigationAction>()
    val navigationEvent: SharedFlow<SwapLoadingNavigationAction> = _navigationEvent

    init {
        baseViewModelScope.launch {
            when (swapType) {
                SwapType.KLAY_TO_DIDA -> {
                    swapKlayToDidaApi(password, amount.toDouble())
                        .onSuccess { _navigationEvent.emit(SwapLoadingNavigationAction.NavigateToSuccess) }
                        .onError { e ->
                            //TODO : 실패 시 로직 구현해야함
                            catchError(e)
                        }
                }
                else -> {
                    swapDidaToKlayAPI(password, amount.toDouble())
                        .onSuccess { _navigationEvent.emit(SwapLoadingNavigationAction.NavigateToSuccess) }
                        .onError { e ->
                            //TODO : 실패 시 로직 구현해야함
                            catchError(e)
                        }
                }
            }
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory{
        fun create(
            @Assisted("swapType") swapType: SwapType,
            @Assisted("password") password: String,
            @Assisted("amount") amount: Float
        ): SwapLoadingViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            swapType: SwapType,
            password: String,
            amount: Float
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(swapType, password, amount) as T
            }
        }
    }
}
