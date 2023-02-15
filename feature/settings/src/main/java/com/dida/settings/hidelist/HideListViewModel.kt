package com.dida.settings.hidelist

import com.dida.common.base.BaseViewModel
import com.dida.common.util.UiState
import com.dida.data.DataApplication
import com.dida.domain.model.nav.detailnft.DetailNFT
import com.dida.domain.model.nav.hide.CardHideList
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CreateUserAPI
import com.dida.domain.usecase.main.HideListAPI
import com.dida.domain.usecase.main.NicknameCheckAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HideListViewModel @Inject constructor(
    private val hideListAPI: HideListAPI
) : BaseViewModel() {

    private val TAG = "HideListViewModel"

    private val _cardHideListState: MutableStateFlow<UiState<List<CardHideList>>> = MutableStateFlow(UiState.Loading)
    val cardHideListState: StateFlow<UiState<List<CardHideList>>> = _cardHideListState

    fun getHideList(){
        baseViewModelScope.launch {
            hideListAPI()
                .onSuccess { _cardHideListState.emit(UiState.Success(it)) }
                .onError { e -> catchError(e) }
        }
    }
}
