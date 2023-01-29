package com.dida.community

import com.dida.common.base.BaseViewModel
import com.dida.common.util.CommunityActionHandler
import com.dida.common.util.CommunityWriteActionHandler
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.nav.post.Posts
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.PostsAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl,
    private val postsAPI: PostsAPI,
) : BaseViewModel(), CommunityActionHandler, CommunityWriteActionHandler {

    private val TAG = "CommunityViewModel"

    private val _myWriteState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val myWriteState: StateFlow<Boolean> = _myWriteState

    private val _moreEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val moreEvent: SharedFlow<Unit> = _moreEvent

    private val _navigationEvent: MutableSharedFlow<CommunityNavigationAction> = MutableSharedFlow<CommunityNavigationAction>()
    val navigationEvent: SharedFlow<CommunityNavigationAction> = _navigationEvent

    private val _postsState: MutableStateFlow<List<Posts>> = MutableStateFlow<List<Posts>>(emptyList())
    val postsState: StateFlow<List<Posts>> = _postsState.asStateFlow()

    init {
        baseViewModelScope.launch {
            postsAPI.invoke()
                .onSuccess { _postsState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    override fun onCommunityItemClicked(communityId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToDetail(communityId))
        }
    }

    override fun onCommunityWriteClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToCommunityWrite)
        }
    }

    // 나의 게시물일 경우 More 버튼, 아닐경우 Clip 버튼
    override fun onClipOrMoreClicked(communityId: Int) {
        baseViewModelScope.launch {
            if(myWriteState.value) {
                _moreEvent.emit(Unit)
            } else {

            }
        }
    }
}
