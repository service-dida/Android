package com.dida.android.presentation.views.createcommunity

import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCommunityViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl
) : BaseViewModel(), CreateCommunityActionHandler {

    private val TAG = "CreateCommunityViewModel"

    private val _navigationEvent: MutableSharedFlow<CreateCommunityNavigationAction> = MutableSharedFlow<CreateCommunityNavigationAction>()
    val navigationEvent: SharedFlow<CreateCommunityNavigationAction> = _navigationEvent

    override fun onNftSelectClicked(nftId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CreateCommunityNavigationAction.NavigateToSelectNft(nftId))
        }
    }
}