package com.dida.buy.nft

import com.dida.common.base.BaseViewModel
import com.dida.domain.model.main.DetailNft
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.BuyNftAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyNftViewModel @Inject constructor(
    private val buyNftAPI: BuyNftAPI
) : BaseViewModel() {

    private val TAG = "BuyNftViewModel"

    private val _navigationEvent: MutableSharedFlow<BuyNftNavigationAction> =
        MutableSharedFlow<BuyNftNavigationAction>()
    val navigationEvent: SharedFlow<BuyNftNavigationAction> = _navigationEvent

    private val _detailNftState: MutableStateFlow<DetailNft?> = MutableStateFlow(null)
    val detailNftState: StateFlow<DetailNft?> = _detailNftState

    fun initDetailNft(cardId : Long, imgUrl : String, title : String, profileUrl : String,nickname : String, price :String , viewerNickname : String){
        baseViewModelScope.launch {
            _detailNftState.emit(DetailNft(cardId,"","","",imgUrl,viewerNickname,nickname,price,profileUrl,title,false,"",0L,0L))
        }
    }
    fun buyNft(password : String, marketId : Long){
        baseViewModelScope.launch {
            showLoading()
            buyNftAPI(password, marketId)
                .onSuccess {
                    _navigationEvent.emit(BuyNftNavigationAction.NavigateToSuccess)
                }
                .onError { e -> catchError(e) }
            dismissLoading()

        }
    }
}