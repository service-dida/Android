package com.dida.block

import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// TODO: 숨김 목록 관련 수정
@HiltViewModel
class BlockViewModel @Inject constructor(
//    private val getUserHideListAPI: UserHideListAPI,
//    private val deleteUserHideAPI: DeleteUserHideAPI
) : BaseViewModel() {

    private val TAG = "BlockViewModel"

//    private val _userState: MutableStateFlow<List<UserHide>> = MutableStateFlow(emptyList())
//    val userListState: StateFlow<List<UserHide>> = _userState.asStateFlow()
//
//    init {
//        getUserHides()
//    }
//
//    private fun getUserHides() {
//        baseViewModelScope.launch {
//            getUserHideListAPI()
//                .onSuccess { _userState.value = it }
//                .onError { e -> catchError(e) }
//        }
//    }
//
//    fun onBlockCancel(userHide: UserHide) {
//        baseViewModelScope.launch {
//            deleteUserHideAPI(userHide.userId)
//                .onSuccess { getUserHides() }
//                .onError { e -> catchError(e) }
//        }
//    }
}
