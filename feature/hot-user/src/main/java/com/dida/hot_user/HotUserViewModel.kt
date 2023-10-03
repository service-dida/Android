package com.dida.hot_user

import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGE_SIZE
import com.dida.domain.Contents
import com.dida.domain.flatMap
import com.dida.domain.main.model.HotSellerPage
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.HotMemberPageUseCase
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
class HotUserViewModel @Inject constructor(
    private val hotMemberPageUseCase: HotMemberPageUseCase,
    private val memberFollowUseCase: MemberFollowUseCase
) : BaseViewModel(), HotUserActionHandler {

    private val TAG = "HotUserViewModel"

    private val _navigateToUser: MutableSharedFlow<Long> = MutableSharedFlow<Long>()
    val navigateToUser: SharedFlow<Long> = _navigateToUser.asSharedFlow()

    private val _hotMemberState: MutableStateFlow<Contents<HotSellerPage>> = MutableStateFlow(
        Contents(page = 0, pageSize = 0, hasNext = true, content = emptyList())
    )
    val hotMemberState: StateFlow<Contents<HotSellerPage>> = _hotMemberState.asStateFlow()

    init {
        baseViewModelScope.launch {
            hotMemberPageUseCase(page = 0, size = PAGE_SIZE)
                .onSuccess { _hotMemberState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    override fun onUserClicked(userId: Long) {
        baseViewModelScope.launch {
            _navigateToUser.emit(userId)
        }
    }

    override fun onUserFollowed(userId: Long) {
        baseViewModelScope.launch {
            showLoading()
            memberFollowUseCase(userId)
                .flatMap { hotMemberPageUseCase(page = hotMemberState.value.page, size = PAGE_SIZE) }
                .onSuccess { _hotMemberState.value = it }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    fun nextPage() {
        baseViewModelScope.launch {
            if (!hotMemberState.value.hasNext) return@launch
            hotMemberPageUseCase(page = hotMemberState.value.page + 1, size = PAGE_SIZE)
                .onSuccess { _hotMemberState.value = it }
                .onError { e -> catchError(e) }
        }
    }

}
