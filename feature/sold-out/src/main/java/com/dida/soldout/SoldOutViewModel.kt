package com.dida.soldout

import com.dida.common.base.BaseViewModel
import com.dida.data.DataApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoldOutViewModel @Inject constructor() : BaseViewModel() {

    private val TAG = "SoldOutViewModel"
}
