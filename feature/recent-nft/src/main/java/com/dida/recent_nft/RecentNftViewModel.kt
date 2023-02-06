package com.dida.recent_nft

import com.dida.common.base.BaseViewModel
import com.dida.data.DataApplication
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CreateUserAPI
import com.dida.domain.usecase.main.NicknameCheckAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentNftViewModel @Inject constructor(
) : BaseViewModel(), RecentNftActionHandler {

    private val TAG = "RecentNftViewModel"

    private val _navigationEvent: MutableSharedFlow<RecentNftNavigationAction> = MutableSharedFlow<RecentNftNavigationAction>()
    val navigationEvent: SharedFlow<RecentNftNavigationAction> = _navigationEvent.asSharedFlow()


}
