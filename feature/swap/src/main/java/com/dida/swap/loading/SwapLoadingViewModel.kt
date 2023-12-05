package com.dida.swap.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.PublicKeyUseCase
import com.dida.domain.usecase.SwapToDidaUseCase
import com.dida.domain.usecase.SwapToKlayUseCase
import com.dida.swap.SwapType
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import encryptWithPublicKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SwapLoadingViewModel @AssistedInject constructor(
    @Assisted("swapType") val swapType: SwapType,
    @Assisted("password") val password: String,
    @Assisted("amount") val amount: Float,
    private val swapToKlayUseCase: SwapToKlayUseCase,
    private val swapToDidaUseCase: SwapToDidaUseCase,
    private val getPublicKeyUseCase: PublicKeyUseCase
) : BaseViewModel() {

    private val TAG = "SwapLoadingViewModel"

    private val _navigateToSuccess: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val navigateToSuccess: SharedFlow<Unit> = _navigateToSuccess.asSharedFlow()

    init {
        swapCoin()
    }

    private fun swapCoin() {
        baseViewModelScope.launch {
            getPublicKeyUseCase()
                .onSuccess {
                    when (swapType) {
                        SwapType.KLAY_TO_DIDA -> swapToDidaUseCase(password.encryptWithPublicKey(it.publicKey), amount.toInt())
                        else -> swapToKlayUseCase(password.encryptWithPublicKey(it.publicKey), amount.toInt())
                    }.onSuccess {
                        delay(2000L)
                        _navigateToSuccess.emit(Unit)
                    }.onError { e -> catchError(e) }
                }.onError { e -> catchError(e) }
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
