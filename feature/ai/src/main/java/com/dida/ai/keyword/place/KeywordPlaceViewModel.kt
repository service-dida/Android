package com.dida.ai.keyword.place

import com.dida.ai.keyword.KeywordViewModelDelegate
import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KeywordPlaceViewModel @Inject constructor(
    keywordViewModelDelegate: KeywordViewModelDelegate
) : BaseViewModel(),
    KeywordViewModelDelegate by keywordViewModelDelegate
{
    private val TAG = "KeywordViewModel"

}
