package com.dida.android.presentation.views.nav.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.domain.ErrorResponse
import com.dida.domain.State
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.usecase.MainUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainUsecase: MainUsecase
    ) : BaseViewModel() {

    private val TAG = "HomeViewModel"

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    private val _mainLiveData = MutableLiveData<Home>()
    val mainLiveData: LiveData<Home>
        get() = _mainLiveData

    private val _termLiveData = MutableLiveData<Int>()
    val termLiveData: LiveData<Int>
        get() = _termLiveData

    private val _soldoutLiveData = MutableLiveData<List<SoldOut>>()
    val soldoutLiveData: LiveData<List<SoldOut>>
        get() = _soldoutLiveData

    fun getMain() {
        viewModelScope.launch {
            mainUsecase.getMainAPI().let {
                when(it.state) {
                    State.SUCCESS -> { _mainLiveData.postValue(it.data as Home) }
                    State.FAIL -> { _errorLiveData.postValue(it.data.toString()) }
                    State.NETWORK_ERROR -> { _errorLiveData.postValue(it.data.toString()) }
                }
            }
        }
    }

    fun getSoldOut(term: Int) {
        viewModelScope.launch {
            mainUsecase.getSoldOutAPI(term).let {
                when(it.state) {
                    State.SUCCESS -> {
                        _soldoutLiveData.postValue(it.data as List<SoldOut>)
                        _termLiveData.postValue(term)
                    }
                    State.FAIL -> { _errorLiveData.postValue(it.data.toString()) }
                    State.NETWORK_ERROR -> { _errorLiveData.postValue(it.data.toString()) }
                }
            }
        }
    }
}