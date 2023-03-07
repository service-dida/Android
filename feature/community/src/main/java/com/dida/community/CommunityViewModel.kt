package com.dida.community

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dida.common.actionhandler.CommunityActionHandler
import com.dida.common.actionhandler.CommunityWriteActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.community.adapter.createPostsPager
import com.dida.domain.model.nav.community.HotCard
import com.dida.domain.model.nav.post.Posts
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.HotCardAPI
import com.dida.domain.usecase.main.PostsAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val postsAPI: PostsAPI,
    private val hotCardAPI: HotCardAPI
) : BaseViewModel(), CommunityActionHandler, CommunityWriteActionHandler, HotCardActionHandler {

    private val TAG = "CommunityViewModel"

    private val _myWriteState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val myWriteState: StateFlow<Boolean> = _myWriteState

    private val _moreEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val moreEvent: SharedFlow<Unit> = _moreEvent

    private val _navigationEvent: MutableSharedFlow<CommunityNavigationAction> = MutableSharedFlow<CommunityNavigationAction>()
    val navigationEvent: SharedFlow<CommunityNavigationAction> = _navigationEvent

    val postsState: Flow<PagingData<Posts>> = createPostsPager(postsAPI = postsAPI)
    .flow.cachedIn(baseViewModelScope)

    private val _hotCardState: MutableStateFlow<UiState<List<HotCard>>> = MutableStateFlow<UiState<List<HotCard>>>(UiState.Loading)
    val hotCardState: StateFlow<UiState<List<HotCard>>> = _hotCardState.asStateFlow()

    init {
        baseViewModelScope.launch {
            hotCardAPI.invoke()
                .onSuccess {
                    delay(SHIMMER_TIME)
                    _hotCardState.value = UiState.Success(it) }
                .onError { e -> catchError(e) }
        }
    }

    override fun onCommunityItemClicked(postId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToDetail(postId))
        }
    }

    override fun onCommunityWriteClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToCommunityWrite)
        }
    }

    // 나의 게시물일 경우 More 버튼, 아닐경우 Clip 버튼
    override fun onClipOrMoreClicked(postId: Long) {
        baseViewModelScope.launch {
            if(myWriteState.value) {
                _moreEvent.emit(Unit)
            } else {

            }
        }
    }

    override fun onHotCardClicked(cardId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToNftDetail(cardId = cardId))
        }
    }
}
