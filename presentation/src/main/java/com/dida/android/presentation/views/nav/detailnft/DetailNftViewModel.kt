package com.dida.android.presentation.views.nav.detailnft

import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.base.UiState
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.nav.detailnft.Community
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailNftViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl
) : BaseViewModel() {

    private val TAG = "DetailNftViewModel"

    private val _detailNftState: MutableStateFlow<UiState<Community>> = MutableStateFlow(UiState.Loading)
    val detailNftState: StateFlow<UiState<Community>> = _detailNftState

    fun getDetailNft() {
        // test용
        // api나오면 사용
//        viewModelScope.launch {
//            mainUsecase.getMainAPI()
//                .onSuccess {
//                    it.catch { e ->
//                        catchError(e)
//                    }
//                    it.collect { data ->
//                        _homeStateFlow.value = UiState.Success(data)
//                        onSoldOutDayClicked(_termStateFlow.value)
//                    }
//                }.onError { e ->
//                    catchError(e)
//                }
//        }
    }

}