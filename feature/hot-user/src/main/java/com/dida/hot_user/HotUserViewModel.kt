package com.dida.hot_user

import androidx.paging.PagingData
import com.dida.common.base.BaseViewModel
import com.dida.domain.model.nav.mypage.UserNft
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HotUserViewModel @Inject constructor(
) : BaseViewModel(), HotUserActionHandler {

    private val TAG = "HotUserViewModel"

    private val _navigationEvent: MutableSharedFlow<HotUserNavigationAction> = MutableSharedFlow<HotUserNavigationAction>()
    val navigationEvent: SharedFlow<HotUserNavigationAction> = _navigationEvent.asSharedFlow()

    var hotUserState: Flow<PagingData<UserNft>> = emptyFlow()

    fun getHotUsers() {
    }

}
