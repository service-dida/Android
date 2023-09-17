package com.dida.block

import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGING_SIZE
import com.dida.domain.main.model.Block
import com.dida.domain.main.model.HideMember
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CancelBlockUseCase
import com.dida.domain.usecase.HideMembersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: 숨김 목록 관련 수정
@HiltViewModel
class BlockViewModel @Inject constructor(
    private val hideMembersUseCase: HideMembersUseCase,
    private val cancelBlockUseCase: CancelBlockUseCase
) : BaseViewModel() {

    private val TAG = "BlockViewModel"

    private val _userState: MutableStateFlow<List<HideMember>> = MutableStateFlow(emptyList())
    val userListState: StateFlow<List<HideMember>> = _userState.asStateFlow()

    init {
        getUserHides()
    }

    private fun getUserHides() {
        baseViewModelScope.launch {
            hideMembersUseCase(0, PAGING_SIZE)
                .onSuccess { _userState.value = it.content }
                .onError { e -> catchError(e) }
        }
    }

    fun onBlockCancel(userHide: HideMember) {
        baseViewModelScope.launch {
            cancelBlockUseCase(type = Block.MEMBER, userHide.memberId)
                .onSuccess { getUserHides() }
                .onError { e -> catchError(e) }
        }
    }
}
