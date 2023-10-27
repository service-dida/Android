package com.dida.ai.keyword.result

import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.MakeAiPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordResultViewModel @Inject constructor(
    private val makeAiPictureUseCase: MakeAiPictureUseCase
) : BaseViewModel() {

    private val TAG = "KeywordResultViewModel"

    private val _navigationAction: MutableSharedFlow<KeywordResultNavigationAction> = MutableSharedFlow<KeywordResultNavigationAction>()
    val navigationAction: SharedFlow<KeywordResultNavigationAction> = _navigationAction.asSharedFlow()

    private val _aiPictures: MutableStateFlow<List<String>> = MutableStateFlow<List<String>>(emptyList())
    val aiPictures: StateFlow<List<String>> = _aiPictures.asStateFlow()

    private val _selectedPicture: MutableStateFlow<String> = MutableStateFlow<String>("")
    val selectedPicture: StateFlow<String> = _selectedPicture.asStateFlow()

    fun createAiPicture(sentence: String) {
        baseViewModelScope.launch {
            showLoading()
            makeAiPictureUseCase(sentence)
                .onSuccess { _aiPictures.value = listOf(it.url1, it.url2, it.url3, it.url4) }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    fun onSelectImage(url: String) {
        baseViewModelScope.launch {
            _selectedPicture.value = url
        }
    }

    fun onRestartKeyword() {
        baseViewModelScope.launch {
            _navigationAction.emit(KeywordResultNavigationAction.NavigateToRestartKeyword)
        }
    }

    fun onDownload() {
        baseViewModelScope.launch {
            _navigationAction.emit(KeywordResultNavigationAction.NavigateToDownloadAiPicture)
        }
    }

    fun onCreateNft() {
        baseViewModelScope.launch {
            _navigationAction.emit(KeywordResultNavigationAction.NavigateToCreateNftDialog)
        }
    }
}
