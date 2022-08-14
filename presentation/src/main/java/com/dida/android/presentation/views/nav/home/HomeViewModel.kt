package com.dida.android.presentation.views.nav.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepository
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.usecase.MainUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainUsecase: MainUsecase
    ) : BaseViewModel() {

    private val TAG = "HomeViewModel"

    private val _errorLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean>
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
                if(it != null) {
                    _mainLiveData.postValue(it)
                }
                else{
                    _errorLiveData.postValue(true)
                }
            }
        }
    }

    fun getSoldOut(term: Int) {
        viewModelScope.launch {
            mainUsecase.getSoldOutAPI(term).let {
                if(it != null) {
                    _soldoutLiveData.postValue(it)
                    _termLiveData.postValue(term)
                }
                else{
                    _errorLiveData.postValue(true)
                }
            }
        }
    }
}