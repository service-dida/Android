package com.dida.ai.keyword.style

import com.dida.common.base.BaseViewModel
import com.dida.domain.model.main.Collection
import com.dida.domain.model.main.Follow
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.PostUserFollowAPI
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
class KeywordStyleViewModel @Inject constructor() : BaseViewModel() {

    private val TAG = "KeywordStyleViewModel"
}
