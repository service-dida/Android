package com.dida.hot_user

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dida.common.base.BaseViewModel
import com.dida.domain.model.nav.home.HotUser
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.usecase.main.HotUserAPI
import com.dida.hot_user.adapter.createHotUserPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HotUserViewModel @Inject constructor(
    private val hotUserAPI: HotUserAPI
) : BaseViewModel(), HotUserActionHandler {

    private val TAG = "HotUserViewModel"

    private val _navigationEvent: MutableSharedFlow<HotUserNavigationAction> = MutableSharedFlow<HotUserNavigationAction>()
    val navigationEvent: SharedFlow<HotUserNavigationAction> = _navigationEvent.asSharedFlow()

    var hotUserState: Flow<PagingData<HotUser>> = createHotUserPager(hotUserAPI = hotUserAPI).flow.cachedIn(baseViewModelScope)

    override fun onUserClicked(userId: Long) {
        TODO("Not yet implemented")
    }

    override fun onUserFollowed() {
        TODO("Not yet implemented")
    }

}
