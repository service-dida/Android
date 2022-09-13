package com.dida.android.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {
    private val _errorEvent: MutableStateFlow<Throwable?> = MutableStateFlow(null)
    val errorEvent: StateFlow<Throwable?> = _errorEvent

    fun catchError(e: Throwable?) {
        _errorEvent.value = e
    }
}