package com.dida.swap.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.SwapToDidaUseCase
import com.dida.domain.usecase.SwapToKlayUseCase
import com.dida.swap.SwapType
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SwapLoadingViewModel @AssistedInject constructor(
    @Assisted("swapType") val swapType: SwapType,
    @Assisted("password") val password: String,
    @Assisted("amount") val amount: Float,
    private val swapToKlayUseCase: SwapToKlayUseCase,
    private val swapToDidaUseCase: SwapToDidaUseCase
) : BaseViewModel() {

    private val TAG = "SwapLoadingViewModel"

    private val _navigationEvent: MutableSharedFlow<SwapLoadingNavigationAction> =
        MutableSharedFlow<SwapLoadingNavigationAction>()
    val navigationEvent: SharedFlow<SwapLoadingNavigationAction> = _navigationEvent

    init {
        //TODO : 실패 시 로직 구현해야함
        baseViewModelScope.launch {
            when (swapType) {
                SwapType.KLAY_TO_DIDA -> {
                    swapToDidaUseCase(password, amount.toInt())
                        .onSuccess { _navigationEvent.emit(SwapLoadingNavigationAction.NavigateToSuccess) }
                        .onError { e -> catchError(e) }
                }
                else -> {
                    swapToKlayUseCase(password, amount.toInt())
                        .onSuccess { _navigationEvent.emit(SwapLoadingNavigationAction.NavigateToSuccess) }
                        .onError { e -> catchError(e) }
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
