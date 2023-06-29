package com.dida.community_detail

import com.dida.common.actionhandler.CommentActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.ui.report.ReportType
import com.dida.common.ui.report.ReportViewModelDelegate
import com.dida.data.DataApplication
import com.dida.domain.flatMap
import com.dida.domain.model.main.Comments
import com.dida.domain.model.main.Post
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.*
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
class DetailCommunityViewModel @Inject constructor(
    private val postIdAPI: PostIdAPI,
    private val commentsPostIdAPI: CommentsPostIdAPI,
    private val commentAPI: CommentAPI,
    private val deleteCommentAPI: DeleteCommentAPI,
    private val deletePostAPI: DeletePostAPI,
    reportViewModelDelegate: ReportViewModelDelegate
) : BaseViewModel(), DetailCommunityActionHandler, CommentActionHandler,
    ReportViewModelDelegate by reportViewModelDelegate {

    private val TAG = "DetailCommunityViewModel"

    private val _navigationEvent: MutableSharedFlow<DetailCommunityNavigationAction> = MutableSharedFlow<DetailCommunityNavigationAction>()
    val navigationEvent: SharedFlow<DetailCommunityNavigationAction> = _navigationEvent.asSharedFlow()

    private val _messageEvent: MutableSharedFlow<DetailCommunityMessageAction> = MutableSharedFlow<DetailCommunityMessageAction>()
    val messageEvent: SharedFlow<DetailCommunityMessageAction> = _messageEvent.asSharedFlow()

    private val _postState: MutableStateFlow<Post?> = MutableStateFlow(null)
    val postState: StateFlow<Post?> = _postState.asStateFlow()

    private val _commentList: MutableStateFlow<List<Comments>> = MutableStateFlow(emptyList())
    val commentList: StateFlow<List<Comments>> = _commentList.asStateFlow()

    private val _commentEmpty: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val commentEmpty: StateFlow<Boolean> = _commentEmpty.asStateFlow()

    val commentState: MutableStateFlow<String> = MutableStateFlow("")

    val isWrite: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    fun getPost(postId: Long) {
        baseViewModelScope.launch {
            showLoading()
            postIdAPI.invoke(postId = postId)
                .onSuccess { _postState.value = it }
                .flatMap { commentsPostIdAPI.invoke(postId = postId) }
                .onSuccess {
                    _commentList.value = it
                    _commentEmpty.value = it.isEmpty()
                    isWrite.value = false }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    fun deleteComment(commentId: Long) {
        baseViewModelScope.launch {
            deleteCommentAPI(commentId = commentId)
                .onSuccess { _messageEvent.emit(DetailCommunityMessageAction.DeleteReplyMessage) }
                .onError { e -> catchError(e) }
        }
    }

    override fun onCommentClicked(postId: Long) {
        baseViewModelScope.launch {
            showLoading()
            if (commentState.value.isNotBlank()) {
                commentAPI(postId = postId, content = commentState.value)
                    .onSuccess { commentState.value = "" }
                    .flatMap { commentsPostIdAPI(postId = postId) }
                    .onSuccess {
                        isWrite.value = true
                        _commentList.value = it
                        _commentEmpty.value = it.isEmpty()
                    }
                    .onError { e -> catchError(e) }
            }
            dismissLoading()
        }
    }

    override fun onCommunityMoreClicked(userId: Long, postId: Long) {
        baseViewModelScope.launch {
            if (DataApplication.dataStorePreferences.getUserId() != userId) {
                _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToNotWriterMore(postId))
            } else {
                _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToWriterMore(postId))
            }
        }
    }

    override fun onCommentUserProfileClicked(userId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToUserProfile(userId = userId))
        }
    }

    fun onPostReport(postId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToPostReport(postId = postId))
        }
    }

    fun onPostBlockClicked(postId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToPostBlock(postId = postId))
        }
    }

    override fun onReportClicked(userId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToUserReport(userId = userId))
        }
    }

    override fun onBlockClicked(userId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToUserBlock(userId = userId))
        }
    }

    override fun onDeleteClicked(commentId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToDelete(commentId = commentId))
        }
    }

    override fun onUpdateClicked(commentId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToUpdate(commentId = commentId))
        }
    }

    override fun onUserProfileClicked(userId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToUserProfile(userId = userId))
        }
    }

    override fun onCardClicked(cardId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToCardDetail(cardId = cardId))
        }
    }

    fun onReport(type: ReportType, reportId: Long, content: String) {
        onReportDelegate(coroutineScope = baseViewModelScope, type = type, reportId = reportId, content = content)
    }

    fun onDeletePost(postId: Long) {
        baseViewModelScope.launch {
            deletePostAPI.invoke(postId)
                .onSuccess {
                    _messageEvent.emit(DetailCommunityMessageAction.DeletePostMessage)
                    _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToBack)
                }
                .onError { e -> catchError(e) }
        }
    }

    fun onPostBlock(type: ReportType, blockId: Long){
        onBlockDelegate(coroutineScope = baseViewModelScope, type = type, blockId = blockId)
    }
}
