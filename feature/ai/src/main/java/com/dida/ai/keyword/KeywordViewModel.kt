package com.dida.ai.keyword

import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel @Inject constructor(): BaseViewModel() {

    private val _keywords: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val keywords: StateFlow<List<String>> = _keywords.asStateFlow()

    fun insertKeyword(keyword: String) {
        val newKeywords: ArrayList<String> = ArrayList<String>().apply {
            this.addAll(_keywords.value)
            add(keyword)
        }
        _keywords.value = newKeywords
    }

    fun deleteKeyword(index: Int) {
        val newKeywords: MutableList<String> = _keywords.value.toMutableList()
        newKeywords.removeAt(index)
        _keywords.value = newKeywords
    }
}
