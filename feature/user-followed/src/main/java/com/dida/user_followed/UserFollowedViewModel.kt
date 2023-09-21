package com.dida.user_followed

import com.dida.common.base.BaseViewModel
import com.dida.domain.main.model.CommonFollow
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CommonFollowUseCase
import com.dida.domain.usecase.MemberFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserFollowedViewModel @Inject constructor(
    private val memberFollowUseCase: MemberFollowUseCase,
    private val commonFollowUseCase: CommonFollowUseCase
) : BaseViewModel() {

    private val TAG = "UserFollowedViewModel"

    private val _followingListState: MutableStateFlow<List<CommonFollow>> = MutableStateFlow(emptyList())
    val followingListState: StateFlow<List<CommonFollow>> = _followingListState.asStateFlow()

    private val _followerListState: MutableStateFlow<List<CommonFollow>> = MutableStateFlow(emptyList())
    val followerListState: StateFlow<List<CommonFollow>> = _followerListState.asStateFlow()

    private val _messageEvent: MutableSharedFlow<UserFollowedMessageAction> = MutableSharedFlow<UserFollowedMessageAction>()
    val messageEvent: SharedFlow<UserFollowedMessageAction> = _messageEvent.asSharedFlow()

    /**
     * TODO : 팔로우 유저 목록 API 연동
     **/
    fun onGetUserFollowed() {

    }

    fun onFollowButtonClicked(user: CommonFollow) = baseViewModelScope.launch {
        memberFollowUseCase(user.memberId)
            .onSuccess {
                _messageEvent.emit(UserFollowedMessageAction.UserUnFollowMessage)
            }.onError { e -> catchError(e) }
    }
}
