package com.dida.add.main

import android.net.Uri
import com.dida.common.base.BaseViewModel
import com.dida.common.util.combineStates
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
class CreateNftViewModel @Inject constructor() : BaseViewModel() {

    private val TAG = "CreateNftViewModel"

    private val _nftImageState: MutableStateFlow<String> = MutableStateFlow<String>("")
    val nftImageState: StateFlow<String> = _nftImageState.asStateFlow()

    val titleTextState: MutableStateFlow<String> = MutableStateFlow("")
    val titleLengthState: MutableStateFlow<Int> = MutableStateFlow(0)

    val descriptionTextState: MutableStateFlow<String> = MutableStateFlow("")
    val descriptionLengthState: MutableStateFlow<Int> = MutableStateFlow(0)

    val hasNextState: StateFlow<Boolean> =
        combineStates(
            flow1 = titleLengthState,
            flow2 = descriptionLengthState,
            flow3 = nftImageState
        ) { title, description, image ->
            (title > 0 && description > 0 && image.isNotBlank())
        }

    private val _navigateToAddPurpose: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateToAddPurpose: SharedFlow<Unit> = _navigateToAddPurpose.asSharedFlow()

    private val _navigateToGallery: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateToGallery: SharedFlow<Unit> = _navigateToGallery.asSharedFlow()

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

    fun onImageClicked() {
        baseViewModelScope.launch {
            _navigateToGallery.emit(Unit)
        }
    }

    fun onNextButtonClicked() {
        baseViewModelScope.launch {
            _navigateToAddPurpose.emit(Unit)
        }
    }

    fun setNFTImage(uri: Uri?){
        _nftImageState.value = uri.toString()
    }
}
