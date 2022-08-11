package com.dida.android.presentation.views.nav.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepository
import com.dida.domain.usecase.MainUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val mainUsecase: MainUsecase) : BaseViewModel() {

    private val TAG = "AddViewModel"

    private val _walletExistsLiveData = MutableLiveData<Boolean>()
    val walletExistsLiveData: LiveData<Boolean>
        get() = _walletExistsLiveData

    private val _errorLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean>
        get() = _errorLiveData

    fun getWalletExists() {
        viewModelScope.launch {
            mainUsecase.getWalletExistsAPI().let {
                if(it != null) {
                    _walletExistsLiveData.postValue(it)
                }
                else{
                    _errorLiveData.postValue(true)
                }
            }
        }
    }
}