package com.dida.ai.keyword.product

import com.dida.ai.keyword.KeywordViewModelDelegate
import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KeywordProductViewModel @Inject constructor(
    keywordViewModelDelegate: KeywordViewModelDelegate
) : BaseViewModel(),
    KeywordViewModelDelegate by keywordViewModelDelegate
{

    private val TAG = "KeywordProductViewModel"
}
