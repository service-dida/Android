package com.dida.ai.keyword

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
class KeywordViewModel @Inject constructor(
    private val makeAiPictureUseCase: MakeAiPictureUseCase
): BaseViewModel() {

    private val _keywords: MutableStateFlow<List<Keyword>> = MutableStateFlow(listOf(Keyword.Init, Keyword.Init, Keyword.Init, Keyword.Init))
    var keywords: StateFlow<List<Keyword>> = _keywords.asStateFlow()

    fun initKeywords() {
        _keywords.value = listOf(Keyword.Init, Keyword.Init, Keyword.Init, Keyword.Init)
    }

    fun insertKeyword(type: KeywordType, keyword: Keyword = Keyword.Init) {
        val insertedKeywords = _keywords.value.toMutableList().apply {
            this[type.index] = keyword
        }
        _keywords.value = insertedKeywords
    }

    fun deleteKeyword(type: KeywordType) {
        val deletedKeywords = _keywords.value.toMutableList().apply {
            this[type.index] = Keyword.Init
        }
        _keywords.value = deletedKeywords
    }

    fun getSentence(): String{
        var sentence = ""
        _keywords.value.forEach { keyword ->
            sentence += "${keyword.word} "
        }
        return sentence
    }

    fun makeAiPicture(){
        baseViewModelScope.launch {
            makeAiPictureUseCase(getSentence())
                .onSuccess {

                }
                .onError { e ->
                    catchError(e)
                }
        }
    }
}

enum class KeywordType(val index: Int) {
    Product(0), Place(1), Style(2), Color(3)
}
