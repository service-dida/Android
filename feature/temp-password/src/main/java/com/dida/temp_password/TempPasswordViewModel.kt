package com.dida.temp_password

import com.dida.common.base.BaseViewModel
import com.dida.common.util.AppLog
import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.flatMap
import com.dida.domain.model.nav.mypage.OtherUserProfie
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TempPasswordViewModel @Inject constructor(
    private val tempPasswordAPI: TempPasswordAPI
) : BaseViewModel() {

    private val TAG = "TempPasswordViewModel"

    init {
        baseViewModelScope.launch {
            tempPasswordAPI()
                .onSuccess {  }
                .onError {  }
        }
    }
}
