package com.dida.user_followed

import com.dida.common.base.BaseViewModel
import com.dida.common.util.INIT_PAGE
import com.dida.common.util.PAGE_SIZE
import com.dida.domain.Contents
import com.dida.domain.main.model.CommonFollow
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CommonFollowUseCase
import com.dida.domain.usecase.CommonFollowingUseCase
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
    private val commonFollowUseCase: CommonFollowUseCase,
    private val commonFollowingUseCase: CommonFollowingUseCase
) : BaseViewModel() {

    private val TAG = "UserFollowedViewModel"

    private val _followingState: MutableStateFlow<Contents<CommonFollow>> = MutableStateFlow(
        Contents(page = INIT_PAGE, pageSize = PAGE_SIZE, content = emptyList())
    )
    val followingState: StateFlow<Contents<CommonFollow>> = _followingState.asStateFlow()

    private val _followerState: MutableStateFlow<Contents<CommonFollow>> = MutableStateFlow(
        Contents(page = INIT_PAGE, pageSize = PAGE_SIZE, content = emptyList())
    )
    val followerState: StateFlow<Contents<CommonFollow>> = _followerState.asStateFlow()

    private val _messageEvent: MutableSharedFlow<UserFollowedMessageAction> = MutableSharedFlow<UserFollowedMessageAction>()
    val messageEvent: SharedFlow<UserFollowedMessageAction> = _messageEvent.asSharedFlow()

    init {
        getFollowerMember()
        getFollowingMember()
    }

    private fun getFollowerMember() {
        baseViewModelScope.launch {
            commonFollowUseCase(page = INIT_PAGE, pageSize = PAGE_SIZE)
                .onSuccess { _followerState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    private fun getFollowingMember() {
        baseViewModelScope.launch {
            baseViewModelScope.launch {
                commonFollowingUseCase(page = INIT_PAGE, pageSize = PAGE_SIZE)
                    .onSuccess { _followingState.value = it }
                    .onError { e -> catchError(e) }
            }
        }
    }

    fun onNextPageFromFollower() {
        baseViewModelScope.launch {
            if (!followerState.value.hasNext) return@launch
            commonFollowUseCase(page = followerState.value.page + 1, pageSize = PAGE_SIZE)
                .onSuccess {
                    it.content = (followerState.value.content.toMutableList()) + it.content
                    _followerState.value = it
                }
                .onError { e -> catchError(e) }
        }
    }

    fun onNextPageFromFollowing() {
        baseViewModelScope.launch {
            if (!followingState.value.hasNext) return@launch
            commonFollowingUseCase(page = followingState.value.page + 1, pageSize = PAGE_SIZE)
                .onSuccess {
                    it.content = (followingState.value.content.toMutableList()) + it.content
                    _followingState.value = it
                }
                .onError { e -> catchError(e) }
        }
    }

    fun onFollowButtonClicked(user: CommonFollow) =
        baseViewModelScope.launch {
            memberFollowUseCase(user.memberId)
                .onSuccess {
                    if (user.isFollowing) _messageEvent.emit(UserFollowedMessageAction.UserUnFollowMessage)
                    else _messageEvent.emit(UserFollowedMessageAction.UserFollowMessage(user.nickname))
                    changeFollowingUser(user)
                    changeFollowerUser(user)
                }
                .onError { e -> catchError(e) }
        }

    private fun changeFollowingUser(user: CommonFollow) {
        val userIndex = followingState.value.content.indexOf(user)
        val newList = followingState.value.content.toMutableList()
        val beforeUser = newList[userIndex]
        val newUser = CommonFollow(
            memberId = beforeUser.memberId,
            nickname = beforeUser.nickname,
            profileImgUrl = beforeUser.profileImgUrl,
            nftCnt = beforeUser.nftCnt,
            isFollowing = !beforeUser.isFollowing
        )
        newList[userIndex] = newUser
        _followingState.value = Contents(page = followingState.value.page, pageSize = followingState.value.pageSize, hasNext = followingState.value.hasNext, content = newList)
    }

    private fun changeFollowerUser(user: CommonFollow) {
        val userIndex = followerState.value.content.indexOf(user)
        val newList = followerState.value.content.toMutableList()
        val beforeUser = newList[userIndex]
        val newUser = CommonFollow(
            memberId = beforeUser.memberId,
            nickname = beforeUser.nickname,
            profileImgUrl = beforeUser.profileImgUrl,
            nftCnt = beforeUser.nftCnt,
            isFollowing = !beforeUser.isFollowing
        )
        newList[userIndex] = newUser
        _followerState.value = Contents(page = followerState.value.page, pageSize = followerState.value.pageSize, hasNext = followerState.value.hasNext, content = newList)
    }
}
