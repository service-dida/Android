package com.dida.android.presentation.views.nav.add.addmain

import android.net.Uri
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.model.HaveNotJwtTokenException
import com.dida.data.model.NeedToWalletException
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CheckPasswordAPI
import com.dida.domain.usecase.main.WalletExistedAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val walletExistedAPI: WalletExistedAPI,
    private val checkPasswordAPI: CheckPasswordAPI,
) : BaseViewModel() {

    private val TAG = "AddViewModel"

    private val _walletExistsState: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val walletExistsState: SharedFlow<Boolean> = _walletExistsState

    private val _nftImageState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val nftImageState: StateFlow<String> = _nftImageState

    val titleTextState: MutableStateFlow<String> = MutableStateFlow("")
    val titleLengthState: MutableStateFlow<Int> = MutableStateFlow(0)

    val descriptionTextState: MutableStateFlow<String> = MutableStateFlow("")
    val descriptionLengthState: MutableStateFlow<Int> = MutableStateFlow(0)

    init {
        baseViewModelScope.launch {
            launch {
                titleTextState.collect {
                    titleLengthState.emit(it.length)
                }
            }

            launch {
                descriptionTextState.collect {
                    descriptionLengthState.emit(it.length)
                }
            }
        }
    }

    fun createWallet() {
        baseViewModelScope.launch {
            _walletExistsState.emit(true)
        }
    }

    fun getWalletExists() {
        baseViewModelScope.launch {
            showLoading()
            walletExistedAPI()
                .onSuccess { _walletExistsState.emit(it) }
                .onError { e ->
                    if(e is NeedToWalletException) _walletExistsState.emit(false)
                    else catchError(e) }
            dismissLoading()
        }
    }

    fun setNFTImage(uri: Uri?){
        _nftImageState.value = uri.toString()
    }

    private val _checkPasswordState: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val checkPasswordState: SharedFlow<Boolean> = _checkPasswordState

    fun checkPassword(password: String) {
        baseViewModelScope.launch {
            showLoading()
            checkPasswordAPI(password)
                .onSuccess {
                    _checkPasswordState.emit(it)
                    dismissLoading() }
                .onError { e -> catchError(e) }
        }
    }
}