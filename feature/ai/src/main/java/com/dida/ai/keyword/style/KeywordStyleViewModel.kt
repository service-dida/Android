package com.dida.ai.keyword.style

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

    private val _keywordsState: MutableStateFlow<List<Pair<Int, String>>> = MutableStateFlow(
        listOf(
            Pair(com.dida.common.R.mipmap.img_keyword_style1_foreground, "Oil painting"),
            Pair(com.dida.common.R.mipmap.img_keyword_style2_foreground, "Henri\nMattisse"),
            Pair(com.dida.common.R.mipmap.img_keyword_style3_foreground, "3D art"),
            Pair(com.dida.common.R.mipmap.img_keyword_style4_foreground, "Water\nPainting"),
            Pair(com.dida.common.R.mipmap.img_keyword_style5_foreground, "Crayon"),
            Pair(com.dida.common.R.mipmap.img_keyword_style6_foreground, "Sketch")
        )
    )
    val keywordsState: StateFlow<List<Pair<Int, String>>> = _keywordsState.asStateFlow()
}
