package com.dida.community

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dida.common.actionhandler.CommunityActionHandler
import com.dida.common.actionhandler.CommunityWriteActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.ui.report.ReportType
import com.dida.common.ui.report.ReportViewModelDelegate
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.community.adapter.createPostsPager
import com.dida.data.DataApplication
import com.dida.data.model.NeedLogin
import com.dida.domain.model.main.HotCard
import com.dida.domain.model.main.Posts
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.HotCardAPI
import com.dida.domain.usecase.main.PostPostHideAPI
import com.dida.domain.usecase.main.PostsAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    postsAPI: PostsAPI,
    private val hotCardAPI: HotCardAPI,
    reportViewModelDelegate: ReportViewModelDelegate
) : BaseViewModel(), CommunityActionHandler, CommunityWriteActionHandler, HotCardActionHandler,
    ReportViewModelDelegate by reportViewModelDelegate {

    private val TAG = "CommunityViewModel"

    private val _navigationEvent: MutableSharedFlow<CommunityNavigationAction> = MutableSharedFlow<CommunityNavigationAction>()
    val navigationEvent: SharedFlow<CommunityNavigationAction> = _navigationEvent.asSharedFlow()

    private val _blockEvent: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val blockEvent: SharedFlow<Unit> = _blockEvent.asSharedFlow()

    val postsState: Flow<PagingData<Posts>> = createPostsPager(postsAPI = postsAPI)
        .flow.cachedIn(baseViewModelScope)

    private val _hotCardState: MutableStateFlow<UiState<List<HotCard>>> = MutableStateFlow<UiState<List<HotCard>>>(UiState.Loading)
    val hotCardState: StateFlow<UiState<List<HotCard>>> = _hotCardState.asStateFlow()


    init {
        baseViewModelScope.launch {
            hotCardAPI().onSuccess {
                delay(SHIMMER_TIME)
                _hotCardState.value = UiState.Success(it)
            }.onError { e -> catchError(e) }
        }
    }

    override fun onCommunityItemClicked(postId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToDetail(postId))
        }
    }

    override fun onCommunityWriteClicked() {
        baseViewModelScope.launch {
            if (DataApplication.dataStorePreferences.getAccessToken() == null) {
                catchError(NeedLogin(e = IOException(), code = 127))
            } else {
                _navigationEvent.emit(CommunityNavigationAction.NavigateToCommunityWrite)
            }
        }
    }

    // TODO : 신고 및 차단 & 수정 삭제 플로우 추가하기
    override fun onReportClicked(postId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToReport(postId))
        }
    }

    override fun onBlockClicked(postId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToBlock(postId))
        }
    }

    fun onPostBlock(type: ReportType, blockId: Long){
        baseViewModelScope.launch {
            onBlockDelegate(coroutineScope = baseViewModelScope, type = type, blockId = blockId)
        }
    }

    override fun onUpdateClicked(postId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToUpdate(postId))
        }
    }

    override fun onDeleteClicked(postId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToDelete(postId))
        }
    }

    override fun onHotCardClicked(postId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CommunityNavigationAction.NavigateToDetail(postId = postId))
        }
    }

    fun onReport(type: ReportType, reportId: Long, content: String) {
        onReportDelegate(coroutineScope = baseViewModelScope, type = type, reportId = reportId, content = content)
    }

    fun onBlock(type: ReportType, blockId: Long) {
        onBlockDelegate(coroutineScope = baseViewModelScope, type = type, blockId = blockId)
    }
}
