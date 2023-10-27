package com.dida.ai.keyword.result

import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.MakeAiPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordResultViewModel @Inject constructor(
    private val makeAiPictureUseCase: MakeAiPictureUseCase
) : BaseViewModel() {

    private val TAG = "KeywordResultViewModel"

    private val _aiPictures: MutableStateFlow<List<String>> = MutableStateFlow<List<String>>(emptyList())
    val aiPictures: StateFlow<List<String>> = _aiPictures.asStateFlow()

    fun createAiPicture(sentence: String) {
        baseViewModelScope.launch {
            makeAiPictureUseCase(sentence)
                .onSuccess { _aiPictures.value = listOf(it.url1, it.url2, it.url3, it.url4) }
                .onError { e -> catchError(e) }
        }
    }
}
