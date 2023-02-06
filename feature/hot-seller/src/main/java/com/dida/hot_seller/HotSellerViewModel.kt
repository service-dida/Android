package com.dida.hot_seller

import androidx.paging.PagingData
import com.dida.common.base.BaseViewModel
import com.dida.domain.model.nav.mypage.UserNft
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HotSellerViewModel @Inject constructor(
) : BaseViewModel(), HotSellerActionHandler {

    private val TAG = "HotSellerViewModel"

    private val _navigationEvent: MutableSharedFlow<HotSellerNavigationAction> = MutableSharedFlow<HotSellerNavigationAction>()
    val navigationEvent: SharedFlow<HotSellerNavigationAction> = _navigationEvent.asSharedFlow()

    var hotSellerState: Flow<PagingData<UserNft>> = emptyFlow()

    fun getHotUsers() {
    }

}
