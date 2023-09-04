package com.dida.ai.keyword

import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel @Inject constructor(): BaseViewModel() {

    private val _keywords: MutableStateFlow<List<String>> = MutableStateFlow(listOf(BLANK, BLANK, BLANK, BLANK))
    var keywords: StateFlow<List<String>> = _keywords.asStateFlow()

    fun initKeywords() {
        _keywords.value = listOf(BLANK, BLANK, BLANK, BLANK)
    }

    fun insertKeyword(type: KeywordType, keyword: String) {
        val insertedKeywords = _keywords.value.toMutableList().apply {
            this[type.index] = keyword
        }
        _keywords.value = insertedKeywords
    }

    fun deleteKeyword(type: KeywordType) {
        val deletedKeywords = _keywords.value.toMutableList().apply {
            this[type.index] = BLANK
        }
        _keywords.value = deletedKeywords
    }

    companion object {
        const val BLANK = ""
    }
}

enum class KeywordType(val index: Int) {
    Product(0), Place(1), Style(2), Color(3)
}
