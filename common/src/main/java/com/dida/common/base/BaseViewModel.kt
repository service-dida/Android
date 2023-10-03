package com.dida.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dida.data.DataApplication
import com.dida.data.model.Auth001Exception
import com.dida.data.model.Auth002Exception
import com.dida.data.model.Auth004Exception
import com.dida.domain.NetworkResult
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

    protected val baseViewModelScope: CoroutineScope = viewModelScope + errorHandler

    private val _baseNavigationEvent: MutableSharedFlow<BaseNavigationAction> = MutableSharedFlow<BaseNavigationAction>()
    val baseNavigationEvent: SharedFlow<BaseNavigationAction> = _baseNavigationEvent

    protected suspend fun catchError(exception: Throwable) {
        when(exception) {
            is Auth001Exception, is Auth004Exception -> {
                DataApplication.dataStorePreferences.removeAccountToken()
                _baseNavigationEvent.emit(BaseNavigationAction.NavigateToLogin)
            }
            is Auth002Exception -> {
                DataApplication.dataStorePreferences.removeAccountToken()
                _baseNavigationEvent.emit(BaseNavigationAction.NavigateToDuplicateLogin)
            }
            else -> _errorEvent.emit(exception)
        }
    }

    protected fun <T> NetworkResult<T>.throwWithRetry(retry: suspend () -> Unit) {
        if (this is NetworkResult.Error)
            throw ErrorWithRetry(exception, baseViewModelScope, retry)
    }

    protected fun throwWithRetry(exception: Throwable, retry: suspend () -> Unit) {
        throw ErrorWithRetry(exception, baseViewModelScope, retry)
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


}

data class ErrorWithRetry(
    val exception: Throwable,
    val retryScope: CoroutineScope,
    val retry: suspend () -> Unit
) : Exception()

