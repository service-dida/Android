package com.dida.hot_user

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dida.common.base.BaseViewModel
import com.dida.domain.model.main.HotUser
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.HotUserAPI
import com.dida.domain.usecase.main.PostUserFollowAPI
import com.dida.hot_user.adapter.createHotUserPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotUserViewModel @Inject constructor(
    private val hotUserAPI: HotUserAPI,
    private val postUserFollowAPI: PostUserFollowAPI
) : BaseViewModel(), HotUserActionHandler {

    private val TAG = "HotUserViewModel"

    private val _navigationEvent: MutableSharedFlow<HotUserNavigationAction> = MutableSharedFlow<HotUserNavigationAction>()
    val navigationEvent: SharedFlow<HotUserNavigationAction> = _navigationEvent.asSharedFlow()

    var hotUserState: Flow<PagingData<HotUser>> = createHotUserPager(hotUserAPI = hotUserAPI).flow.cachedIn(baseViewModelScope)

    override fun onUserClicked(userId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HotUserNavigationAction.NavigateToUserProfile(userId = userId))
        }
    }

    override fun onUserFollowed(userId: Long) {
        baseViewModelScope.launch {
            showLoading()
            postUserFollowAPI(userId)
                .onSuccess { _navigationEvent.emit(HotUserNavigationAction.NavigateToFollow) }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

}
