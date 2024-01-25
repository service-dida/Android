package com.dida.ai.keyword.style

import com.dida.ai.keyword.Keyword
import com.dida.ai.keyword.KeywordViewModelDelegate
import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class KeywordStyleViewModel @Inject constructor(
    keywordViewModelDelegate: KeywordViewModelDelegate
) : BaseViewModel(),
    KeywordViewModelDelegate by keywordViewModelDelegate {

    private val TAG = "KeywordStyleViewModel"

    private val _keywordsState: MutableStateFlow<List<Keyword.Style>> = MutableStateFlow(
        listOf(
            Keyword.Style(com.dida.common.R.mipmap.img_keyword_style1_foreground, word = "Oil painting"),
            Keyword.Style(com.dida.common.R.mipmap.img_keyword_style2_foreground, "Henri Mattisse"),
            Keyword.Style(com.dida.common.R.mipmap.img_keyword_style3_foreground, "3D art"),
            Keyword.Style(com.dida.common.R.mipmap.img_keyword_style4_foreground, "Water Painting"),
            Keyword.Style(com.dida.common.R.mipmap.img_keyword_style5_foreground, "Crayon"),
            Keyword.Style(com.dida.common.R.mipmap.img_keyword_style6_foreground, "Sketch"),
        )
    )
    val keywordsState: StateFlow<List<Keyword.Style>> = _keywordsState.asStateFlow()
}
