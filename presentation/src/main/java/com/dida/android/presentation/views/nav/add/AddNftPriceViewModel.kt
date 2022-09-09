package com.dida.android.presentation.views.nav.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dida.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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