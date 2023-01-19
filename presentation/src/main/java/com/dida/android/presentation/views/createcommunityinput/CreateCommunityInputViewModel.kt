package com.dida.android.presentation.views.createcommunityinput

import com.dida.common.base.BaseViewModel
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.nav.community.CreateCommunityNft
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCommunityInputViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl
) : BaseViewModel(), CreateCommunityInputActionHandler {

    private val TAG = "CreateCommunityInputViewModel"

    private val _navigationEvent: MutableSharedFlow<CreateCommunityInputNavigationAction> = MutableSharedFlow<CreateCommunityInputNavigationAction>()
    val navigationEvent: SharedFlow<CreateCommunityInputNavigationAction> = _navigationEvent.asSharedFlow()

    private val _currentNftState: MutableStateFlow<CreateCommunityNft?> = MutableStateFlow<CreateCommunityNft?>(null)
    val currentNftState: StateFlow<CreateCommunityNft?> = _currentNftState.asStateFlow()

    val _isCreateState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)

    fun setCreateState(isCreate: Boolean) {
        _isCreateState.value = isCreate
    }

    override fun onBackButtonClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(CreateCommunityInputNavigationAction.NavigateToBack)
        }
    }

}
