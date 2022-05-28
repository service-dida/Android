package com.dida.android.presentation.viewmodel

import com.dida.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor() : BaseViewModel() {

    private val TAG = "NicknameViewModel"
}