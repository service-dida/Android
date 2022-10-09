package com.dida.android.presentation.views.nav.community

import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl
) : BaseViewModel(), CommunityActionHandler, CommunityWriteActionHandler {

    private val TAG = "CommunityViewModel"

    private val _navigationEvent: MutableSharedFlow<CommunityNavigationAction> = MutableSharedFlow<CommunityNavigationAction>()
    val navigationEvent: SharedFlow<CommunityNavigationAction> = _navigationEvent

    override fun onCommunityItemClicked(communityId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToDetail(communityId))
        }
    }

    override fun onCommunityWrtieClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToCommunityWrite)
        }
    }
}