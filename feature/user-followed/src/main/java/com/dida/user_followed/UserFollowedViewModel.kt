package com.dida.user_followed

import com.dida.common.base.BaseViewModel
import com.dida.domain.model.main.Collection
import com.dida.domain.model.main.Follow
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.PostUserFollowAPI
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
    private val postUserFollowAPI: PostUserFollowAPI
) : BaseViewModel() {

    private val TAG = "UserFollowedViewModel"

    private val _followingListState: MutableStateFlow<List<Collection>> = MutableStateFlow(emptyList())
    val followingListState: StateFlow<List<Collection>> = _followingListState.asStateFlow()

    private val _followerListState: MutableStateFlow<List<Collection>> = MutableStateFlow(emptyList())
    val followerListState: StateFlow<List<Collection>> = _followerListState.asStateFlow()

    private val _messageEvent: MutableSharedFlow<UserFollowedMessageAction> = MutableSharedFlow<UserFollowedMessageAction>()
    val messageEvent: SharedFlow<UserFollowedMessageAction> = _messageEvent.asSharedFlow()

    /**
     * TODO : 팔로우 유저 목록 API 연동
     **/
    fun onGetUserFollowed(type: Follow) {

    }

    fun onFollowButtonClicked(user: Collection) = baseViewModelScope.launch {
        postUserFollowAPI(user.userId)
            .onSuccess {
                if (user.follow) _messageEvent.emit(UserFollowedMessageAction.UserUnFollowMessage)
                else _messageEvent.emit(UserFollowedMessageAction.UserFollowMessage(user.userName))
            }.onError { e -> catchError(e) }
    }
}