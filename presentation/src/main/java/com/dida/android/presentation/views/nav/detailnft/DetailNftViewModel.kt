package com.dida.android.presentation.views.nav.detailnft

import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.base.UiState
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.nav.detailnft.Community
import com.dida.domain.model.nav.home.Home
import com.dida.domain.onError
import com.dida.domain.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailNftViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl
) : BaseViewModel() {

    private val TAG = "DetailNftViewModel"

    private val _detailNftStateFlow: MutableStateFlow<UiState<Community>> = MutableStateFlow(UiState.Loading)
    val detailNftStateFlow: StateFlow<UiState<Community>> = _detailNftStateFlow

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