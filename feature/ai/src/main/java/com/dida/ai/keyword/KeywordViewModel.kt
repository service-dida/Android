package com.dida.ai.keyword

import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel @Inject constructor(): BaseViewModel() {

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
}

enum class KeywordType(val index: Int) {
    Product(0), Place(1), Style(2), Color(3)
}
