package com.dida.android.presentation.views.nav.add

import android.net.Uri
import com.dida.android.presentation.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CheckPasswordAPI
import com.dida.domain.usecase.main.WalletExistedAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val walletExistedAPI: WalletExistedAPI,
    private val checkPasswordAPI: CheckPasswordAPI,
) : BaseViewModel() {

    private val TAG = "AddViewModel"

    private val _walletExistsState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val walletExistsState: StateFlow<Boolean> = _walletExistsState

    private val _nftImageState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val nftImageState: StateFlow<String> = _nftImageState

    private val _titleLengthState: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val titleLengthState: StateFlow<Int> = _titleLengthState

    private val _descriptionLengthState: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val descriptionLengthState: StateFlow<Int> = _descriptionLengthState

    fun getWalletExists() {
        baseViewModelScope.launch {
            walletExistedAPI()
                .onSuccess {
                    _walletExistsState.value = it
                }.onError { e ->
                    catchError(e)
                }
        }
    }

    fun setNFTImage(uri: Uri?){
        _nftImageState.value = uri.toString()
    }

    fun setTitleLength(length : Int){
        _titleLengthState.value = length
    }

    fun setDescriptionLength(length : Int){
        _descriptionLengthState.value = length
    }

    private val _checkPasswordState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val checkPasswordState: StateFlow<Boolean> = _checkPasswordState

    fun checkPassword(password: String) {
        baseViewModelScope.launch {
            checkPasswordAPI(password)
                .onSuccess {
                    _checkPasswordState.value = it
                }.onError { e ->
                    catchError(e)
                }
        }
    }
}