package com.dida.community

import com.dida.common.base.BaseViewModel
import com.dida.common.util.CommunityActionHandler
import com.dida.common.util.CommunityWriteActionHandler
import com.dida.data.repository.MainRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl
) : BaseViewModel(), CommunityActionHandler, CommunityWriteActionHandler {

    private val TAG = "CommunityViewModel"

    private val _myWriteState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val myWriteState: StateFlow<Boolean> = _myWriteState

    private val _moreEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val moreEvent: SharedFlow<Unit> = _moreEvent

    private val _navigationEvent: MutableSharedFlow<CommunityNavigationAction> = MutableSharedFlow<CommunityNavigationAction>()
    val navigationEvent: SharedFlow<CommunityNavigationAction> = _navigationEvent

    override fun onCommunityItemClicked(communityId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToDetail(communityId))
        }
    }

    override fun onCommunityWriteClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToCommunityWrite)
        }
    }

    // 나의 게시물일 경우 More 버튼, 아닐경우 Clip 버튼
    override fun onClipOrMoreClicked(communityId: Int) {
        baseViewModelScope.launch {
            if(myWriteState.value) {
                _moreEvent.emit(Unit)
            } else {

            }
        }
    }
}
