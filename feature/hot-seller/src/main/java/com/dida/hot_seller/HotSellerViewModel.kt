package com.dida.hot_seller

import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGE_SIZE
import com.dida.domain.Contents
import com.dida.domain.main.model.HotSellerInfo
import com.dida.domain.main.model.HotSellerPage
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.HotSellerPageUseCase
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
class HotSellerViewModel @Inject constructor(
    private val hotSellerPageUseCase: HotSellerPageUseCase,
    private val memberFollowUseCase: MemberFollowUseCase,
) : BaseViewModel(), HotSellerActionHandler {

    private val TAG = "HotSellerViewModel"

    private val _navigationEvent: MutableSharedFlow<HotSellerNavigationAction> = MutableSharedFlow<HotSellerNavigationAction>()
    val navigationEvent: SharedFlow<HotSellerNavigationAction> = _navigationEvent.asSharedFlow()

    private val _hotSellerState: MutableStateFlow<Contents<HotSellerPage>> = MutableStateFlow(
        Contents(page = 0, pageSize = 0, hasNext = true, content = emptyList())
    )
    val hotSellerState: StateFlow<Contents<HotSellerPage>> = _hotSellerState.asStateFlow()

    private val _messageEvent: MutableSharedFlow<HotSellerMessageAction> = MutableSharedFlow<HotSellerMessageAction>()
    val messageEvent: SharedFlow<HotSellerMessageAction> = _messageEvent.asSharedFlow()

    init {
        baseViewModelScope.launch {
            hotSellerPageUseCase(page = 0, size = PAGE_SIZE)
                .onSuccess { _hotSellerState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    fun onNextPage() {
        baseViewModelScope.launch {
            if (!hotSellerState.value.hasNext) return@launch
            hotSellerPageUseCase(page = hotSellerState.value.page + 1, size = PAGE_SIZE)
                .onSuccess {
                    it.content = (hotSellerState.value.content.toMutableList()) + it.content
                    _hotSellerState.value = it
                }.onError { e -> catchError(e) }
        }
    }

    fun onFollowButtonClicked(hotSellerPage: HotSellerPage) {
        baseViewModelScope.launch {
            memberFollowUseCase(hotSellerPage.memberInfo.memberId)
                .onSuccess {
                    if (hotSellerPage.memberInfo.followed) _messageEvent.emit(HotSellerMessageAction.UserUnFollowMessage)
                    else _messageEvent.emit(HotSellerMessageAction.UserFollowMessage(hotSellerPage.memberInfo.memberName))
                    changeUserFollowed(hotSellerPage = hotSellerPage)
                }
                .onError { e -> catchError(e) }
        }
    }


    private fun changeUserFollowed(hotSellerPage: HotSellerPage) {
        val userIndex = hotSellerState.value.content.indexOf(hotSellerPage)
        val newList = hotSellerState.value.content.toMutableList()

        val before = newList[userIndex]
        val beforeMemberInfo = before.memberInfo
        val beforeImgUrls = before.nftImgUrl

        val newMemberInfo = HotSellerInfo(
            memberId = beforeMemberInfo.memberId,
            memberName = beforeMemberInfo.memberName,
            profileUrl = beforeMemberInfo.profileUrl,
            nftCount = beforeMemberInfo.nftCount,
            followed = !beforeMemberInfo.followed,
            me = beforeMemberInfo.me
        )
        val newData = HotSellerPage(
            memberInfo = newMemberInfo,
            nftImgUrl = beforeImgUrls
        )

        newList[userIndex] = newData
        _hotSellerState.value = Contents(page = hotSellerState.value.page, pageSize = hotSellerState.value.pageSize, hasNext = hotSellerState.value.hasNext, content = newList)
    }
}
