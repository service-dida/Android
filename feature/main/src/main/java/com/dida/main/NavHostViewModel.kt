package com.dida.main

import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavHostViewModel @Inject constructor() : BaseViewModel() {

    private val TAG = "NavHostViewModel"
}
