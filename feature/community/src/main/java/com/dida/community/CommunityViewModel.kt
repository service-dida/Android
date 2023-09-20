package com.dida.community

import com.dida.common.actionhandler.CommunityActionHandler
import com.dida.common.actionhandler.CommunityWriteActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.ui.report.ReportViewModelDelegate
import com.dida.common.util.INIT_PAGE
import com.dida.common.util.PAGE_SIZE
import com.dida.common.util.SHIMMER_TIME
import com.dida.common.util.UiState
import com.dida.data.DataApplication
import com.dida.data.model.Auth001Exception
import com.dida.domain.Contents
import com.dida.domain.flatMap
import com.dida.domain.main.model.Block
import com.dida.domain.main.model.HotPost
import com.dida.domain.main.model.Post
import com.dida.domain.main.model.Report
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.HotPostsUseCase
import com.dida.domain.usecase.PostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val postsUseCase: PostsUseCase,
    private val hotPostsUseCase: HotPostsUseCase,
    reportViewModelDelegate: ReportViewModelDelegate
) : BaseViewModel(), CommunityActionHandler, CommunityWriteActionHandler, HotCardActionHandler,
    ReportViewModelDelegate by reportViewModelDelegate {

    private val TAG = "CommunityViewModel"

    private val _navigationEvent: MutableSharedFlow<CommunityNavigationAction> = MutableSharedFlow<CommunityNavigationAction>()
    val navigationEvent: SharedFlow<CommunityNavigationAction> = _navigationEvent.asSharedFlow()

    private val _postsState: MutableStateFlow<Contents<Post>> = MutableStateFlow(
        Contents(page = INIT_PAGE, pageSize = PAGE_SIZE, content = emptyList())
    )
    val postsState: StateFlow<Contents<Post>> = _postsState.asStateFlow()

    private val _hotCardState: MutableStateFlow<UiState<List<HotPost>>> = MutableStateFlow<UiState<List<HotPost>>>(UiState.Loading)
    val hotCardState: StateFlow<UiState<List<HotPost>>> = _hotCardState.asStateFlow()

    init {
        getCommunity()
    }

    fun getCommunity() {
        baseViewModelScope.launch {
            postsUseCase(postsState.value.page, PAGE_SIZE)
                .onSuccess {
                    delay(SHIMMER_TIME)
                    _postsState.value = it }
                .flatMap { hotPostsUseCase(INIT_PAGE, PAGE_SIZE / 2) }
                .onSuccess {
                    delay(SHIMMER_TIME)
                    _hotCardState.value = UiState.Success(it.content)
                }.onError { e -> catchError(e) }
        }
    }

    fun onNextPage() {
        baseViewModelScope.launch {
            if (!postsState.value.hasNext) return@launch
            postsUseCase(postsState.value.page + 1, PAGE_SIZE)
                .onSuccess {
                    delay(SHIMMER_TIME)
                    _postsState.value = it }
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
            if (DataApplication.dataStorePreferences.getAccessToken() == null) {
                catchError(Auth001Exception(e = IOException()))
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

    fun onReport(type: Report, reportId: Long, content: String) = baseViewModelScope.launch {
        onReportDelegate(type = type, reportId = reportId, content = content)
    }

    fun onBlock(type: Block, blockId: Long) = baseViewModelScope.launch {
        onBlockDelegate(type = type, blockId = blockId)
    }
}
