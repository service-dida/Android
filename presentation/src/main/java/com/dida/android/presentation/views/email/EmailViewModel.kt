package com.dida.android.presentation.views.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.DataApplication.Companion.mySharedPreferences
import com.dida.data.repository.MainRepository
import com.dida.domain.usecase.MainUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val mainUsecase: MainUsecase
    ) :
    BaseViewModel() {
    private val TAG = "EmailViewModel"

    private val _sendEmailLiveData = MutableLiveData<String>()
    val sendEmailLiveData: LiveData<String>
        get() = _sendEmailLiveData

    private val _errorLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean>
        get() = _errorLiveData

    fun getSendEmail() {
        viewModelScope.launch {
            mainUsecase.getSendEmailAPI().let {
                if(it != null) {
                    _sendEmailLiveData.postValue(it.random)
                }
                else{
                    _errorLiveData.postValue(true)
                }
            }
        }
    }
}