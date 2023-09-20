package com.dida.buy.nft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.base.BaseViewModel
import com.dida.data.model.Wallet008Exception
import com.dida.domain.flatMap
import com.dida.domain.main.model.CommonProfile
import com.dida.domain.main.model.Nft
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.BuyNftUseCase
import com.dida.domain.usecase.CommonProfileUseCase
import com.dida.domain.usecase.NftDetailUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BuyNftViewModel @AssistedInject constructor(
    @Assisted("buyCard") val nftId: Long,
    private val buyNftUseCase: BuyNftUseCase,
    private val nftDetailUseCase: NftDetailUseCase,
    private val commonProfileUseCase: CommonProfileUseCase
) : BaseViewModel() {

    private val TAG = "BuyNftViewModel"

    private val _navigationEvent: MutableSharedFlow<BuyNftNavigationAction> = MutableSharedFlow<BuyNftNavigationAction>()
    val navigationEvent: SharedFlow<BuyNftNavigationAction> = _navigationEvent

    private val _detailNftState: MutableStateFlow<Nft?> = MutableStateFlow(null)
    val detailNftState: StateFlow<Nft?> = _detailNftState

    private val _userState: MutableStateFlow<CommonProfile?> = MutableStateFlow(null)
    val userState: StateFlow<CommonProfile?> = _userState.asStateFlow()

    init {
        baseViewModelScope.launch {
            nftDetailUseCase(nftId = nftId)
                .onSuccess { _detailNftState.value = it }
                .flatMap { commonProfileUseCase() }
                .onSuccess { _userState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    fun buyNft(password: String) {
        baseViewModelScope.launch {
            showLoading()
            detailNftState.value?.let { nft ->
                buyNftUseCase(password, nft.nftInfo.nftId)
                    .onSuccess { _navigationEvent.emit(BuyNftNavigationAction.NavigateToSuccess(nft.nftInfo.nftId)) }
                    .onError { e ->
                        if (e is Wallet008Exception) _navigationEvent.emit(BuyNftNavigationAction.NavigateToFailAlert)
                        else catchError(e)
                    }
            }
            dismissLoading()
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory{
        fun create(
            @Assisted("nftId") nftId: Long
        ): BuyNftViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            nftId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(nftId) as T
            }
        }
    }
}
