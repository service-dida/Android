package com.dida.buy.nft

import com.dida.common.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class BuyNftViewModel @Inject constructor(
) : BaseViewModel() {

    private val TAG = "BuyNftViewModel"

    private val _navigationEvent: MutableSharedFlow<BuyNftNavigationAction> =
        MutableSharedFlow<BuyNftNavigationAction>()
    val navigationEvent: SharedFlow<BuyNftNavigationAction> = _navigationEvent

}