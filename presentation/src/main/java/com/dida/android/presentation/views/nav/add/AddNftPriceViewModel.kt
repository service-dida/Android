package com.dida.android.presentation.views.nav.add

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepository
import com.dida.domain.usecase.MainUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class AddNftPriceViewModel @Inject constructor() : BaseViewModel() {

    private val TAG = "AddNftPriceViewModel"

    private val _okBtnStateLiveData = MutableLiveData<Boolean>()
    val okBtnStateLiveData: LiveData<Boolean>
        get() = _okBtnStateLiveData

    fun okBtnState(state: Boolean) {
        _okBtnStateLiveData.postValue(state)
    }
}