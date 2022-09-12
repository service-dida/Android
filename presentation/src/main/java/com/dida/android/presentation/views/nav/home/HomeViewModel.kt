package com.dida.android.presentation.views.nav.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.domain.ErrorResponse
import com.dida.domain.State
import com.dida.domain.model.nav.home.Collection
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.HotSeller
import com.dida.domain.model.nav.home.Hots
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.MainUsecase
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainUsecase: MainUsecase
    ) : BaseViewModel() {

    private val TAG = "HomeViewModel"

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _mainLiveData = MutableLiveData<Home>()
    val mainLiveData: LiveData<Home> = _mainLiveData

    private val _termLiveData = MutableLiveData<Int>()
    val termLiveData: LiveData<Int> = _termLiveData

    private val _soldoutLiveData = MutableLiveData<List<SoldOut>>()
    val soldoutLiveData: LiveData<List<SoldOut>> = _soldoutLiveData

    fun getMain() {
        viewModelScope.launch {
            mainUsecase.getMainAPI()
                .onSuccess {
                    _mainLiveData.postValue(it)
                }.onError {
                    _errorLiveData.postValue(it.message)
                }
        }
    }

    fun getSoldOut(term: Int) {
        viewModelScope.launch {
            mainUsecase.getSoldOutAPI(term)
                .onSuccess {
                    _soldoutLiveData.postValue(it)
                    _termLiveData.postValue(term)
                }.onError {
                    _errorLiveData.postValue(it.message)
                }
        }
    }
}