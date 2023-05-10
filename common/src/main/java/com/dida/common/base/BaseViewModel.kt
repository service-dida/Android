package com.dida.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dida.data.DataApplication
import com.dida.data.model.HaveNotJwtTokenException
import com.dida.data.model.InvalidJwtTokenException
import com.dida.data.model.InvalidKakaoAccessTokenException
import com.dida.data.model.NeedLogin
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel() {

    private val _errorEvent: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorEvent: SharedFlow<Throwable> = _errorEvent.asSharedFlow()

    private val _loadingEvent: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val loadingEvent: SharedFlow<Boolean> = _loadingEvent.asSharedFlow()

    private val errorHandler = CoroutineExceptionHandler { CoroutineContext, throwable ->
        viewModelScope.launch(CoroutineContext) {
            _errorEvent.emit(throwable)
        }
    }

    private val _needLoginEvent: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val needLoginEvent: SharedFlow<Boolean> = _needLoginEvent

    suspend fun catchError(e: Throwable?) {
        e?.let {
            when(it) {
                is HaveNotJwtTokenException, is InvalidKakaoAccessTokenException, is NeedLogin -> {
                    DataApplication.dataStorePreferences.removeAccountToken()
                    _needLoginEvent.emit(true)
                }
            }
        }
    }

    fun showLoading() {
        baseViewModelScope.launch {
            _loadingEvent.emit(true)
        }
    }

    fun dismissLoading() {
        baseViewModelScope.launch {
            _loadingEvent.emit(false)
        }
    }

    protected val baseViewModelScope: CoroutineScope = viewModelScope + errorHandler
}
