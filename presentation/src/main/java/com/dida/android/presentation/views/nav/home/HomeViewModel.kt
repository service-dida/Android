package com.dida.android.presentation.views.nav.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.base.UiState
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.MainUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainUsecase: MainUsecase
    ) : BaseViewModel() {

    private val TAG = "HomeViewModel"

    private val _homeStateFlow: MutableStateFlow<UiState<Home>> = MutableStateFlow(UiState.Loading)
    val homeStateFlow: StateFlow<UiState<Home>> = _homeStateFlow

    private val _soldoutStateFlow: MutableStateFlow<UiState<List<SoldOut>>> = MutableStateFlow(UiState.Loading)
    val soldoutStateFlow: StateFlow<UiState<List<SoldOut>>> = _soldoutStateFlow

    private val _termStateFlow: MutableStateFlow<Int> = MutableStateFlow(7)
    val termStateFlow: StateFlow<Int> = _termStateFlow

    fun getMain() {
        viewModelScope.launch {
            mainUsecase.getMainAPI()
                .onSuccess {
                    it.catch { e ->
                        _homeStateFlow.value = UiState.Error(e)
                    }
                    it.collect { data ->
                        _homeStateFlow.value = UiState.Success(data)
                    }
                }.onError {
                    _homeStateFlow.value = UiState.Error(it)
                }
        }
    }

    fun getSoldOut(term: Int) {
        viewModelScope.launch {
            mainUsecase.getSoldOutAPI(term)
                .onSuccess {
                    it.catch { e ->
                        _soldoutStateFlow.value = UiState.Error(e)
                    }
                    it.collect { data ->
                        _soldoutStateFlow.value = UiState.Success(data)
                        _termStateFlow.value = term
                    }
                }.onError {
                    _soldoutStateFlow.value = UiState.Error(it)
                }
        }
    }
}