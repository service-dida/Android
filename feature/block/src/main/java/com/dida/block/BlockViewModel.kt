package com.dida.block

import com.dida.common.base.BaseViewModel
import com.dida.data.DataApplication
import com.dida.domain.model.main.HideCard
import com.dida.domain.model.main.UserHide
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.DeleteUserHideAPI
import com.dida.domain.usecase.main.HideListAPI
import com.dida.domain.usecase.main.UserHideListAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockViewModel @Inject constructor(
    private val getUserHideListAPI: UserHideListAPI,
    private val deleteUserHideAPI: DeleteUserHideAPI
) : BaseViewModel() {

    private val TAG = "BlockViewModel"

    private val _userState: MutableStateFlow<List<UserHide>> = MutableStateFlow(emptyList())
    val userListState: StateFlow<List<UserHide>> = _userState.asStateFlow()

    init {
        getUserHides()
    }

    private fun getUserHides() {
        baseViewModelScope.launch {
            getUserHideListAPI()
                .onSuccess { _userState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    fun onBlockCancel(userHide: UserHide) {
        baseViewModelScope.launch {
            deleteUserHideAPI(userHide.userId)
                .onSuccess { getUserHides() }
                .onError { e -> catchError(e) }
        }
    }
}
