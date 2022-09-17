package com.dida.android.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel() {
    private val _errorEvent: MutableStateFlow<Throwable?> = MutableStateFlow(null)
    val errorEvent: StateFlow<Throwable?> = _errorEvent

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        _errorEvent.value = throwable as Exception
    }

    fun catchError(e: Throwable?) {
        _errorEvent.value = e
    }

    protected val baseViewModelScope: CoroutineScope = viewModelScope + errorHandler
}