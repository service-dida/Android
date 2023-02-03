package com.dida.create_community_input

import com.dida.common.base.BaseViewModel
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.nav.community.CreateCommunityNft
import com.dida.domain.model.nav.detailnft.DetailNFT
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.DetailNftAPI
import com.dida.domain.usecase.main.PostsCardCardIdAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCommunityInputViewModel @Inject constructor(
    private val detailNftAPI: DetailNftAPI
) : BaseViewModel(), CreateCommunityInputActionHandler {

    private val TAG = "CreateCommunityInputViewModel"

    private val _navigationEvent: MutableSharedFlow<CreateCommunityInputNavigationAction> = MutableSharedFlow<CreateCommunityInputNavigationAction>()
    val navigationEvent: SharedFlow<CreateCommunityInputNavigationAction> = _navigationEvent.asSharedFlow()

    private val _currentNftState: MutableStateFlow<DetailNFT?> = MutableStateFlow<DetailNFT?>(null)
    val currentNftState: StateFlow<DetailNFT?> = _currentNftState.asStateFlow()

    val _isCreateState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(true)

    fun setCreateState(isCreate: Boolean) {
        _isCreateState.value = isCreate
    }

    fun getCardDetail(cardId: Long) {
        baseViewModelScope.launch {
            detailNftAPI(cardId = cardId)
                .onSuccess { _currentNftState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    override fun onBackButtonClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(CreateCommunityInputNavigationAction.NavigateToBack)
        }
    }

}
