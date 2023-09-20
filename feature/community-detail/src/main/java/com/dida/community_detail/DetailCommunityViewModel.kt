package com.dida.community_detail

import com.dida.common.actionhandler.CommentActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.common.ui.report.ReportType
import com.dida.common.ui.report.ReportViewModelDelegate
import com.dida.common.util.PAGE_SIZE
import com.dida.data.DataApplication
import com.dida.domain.Contents
import com.dida.domain.flatMap
import com.dida.domain.main.model.Comment
import com.dida.domain.main.model.Post
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.CommentsFromPostUserUseCase
import com.dida.domain.usecase.DeletePostCommentsUseCase
import com.dida.domain.usecase.DeletePostUseCase
import com.dida.domain.usecase.PostsDetailUseCase
import com.dida.domain.usecase.WritePostCommentsUseCase
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
    private val postsDetailUseCase: PostsDetailUseCase,
    private val commentsFromPostUserUseCase: CommentsFromPostUserUseCase,
    private val writePostCommentsUseCase: WritePostCommentsUseCase,
    private val deletePostCommentsUseCase: DeletePostCommentsUseCase,
    private val deletePostUseCase: DeletePostUseCase,
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

    private val _comments: MutableStateFlow<Contents<Comment>> = MutableStateFlow(
        Contents(page = 0, pageSize = 0, hasNext = true, content = emptyList())
    )
    val comments: StateFlow<Contents<Comment>> = _comments.asStateFlow()

    private val _commentEmpty: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val commentEmpty: StateFlow<Boolean> = _commentEmpty.asStateFlow()

    val commentState: MutableStateFlow<String> = MutableStateFlow("")

    val isWrite: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    fun getPost(postId: Long) {
        baseViewModelScope.launch {
            showLoading()
            postsDetailUseCase(postId = postId)
                .onSuccess { _postState.value = it }
                .flatMap { commentsFromPostUserUseCase.invoke(postId = postId, 0 , PAGE_SIZE) }
                .onSuccess {
                    _comments.value = it
                    _commentEmpty.value = it.content.isEmpty()
                    isWrite.value = false
                }.onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    fun deleteComment(commentId: Long) {
        baseViewModelScope.launch {
            deletePostCommentsUseCase(commentId = commentId)
                .onSuccess { _messageEvent.emit(DetailCommunityMessageAction.DeleteReplyMessage) }
                .onError { e -> catchError(e) }
        }
    }

    override fun onCommentClicked(postId: Long) {
        baseViewModelScope.launch {
            showLoading()
            if (commentState.value.isNotBlank()) {
                writePostCommentsUseCase(postId = postId, content = commentState.value)
                    .onSuccess { commentState.value = "" }
                    .flatMap {
                        commentsFromPostUserUseCase(
                            postId = postId,
                            page = comments.value.page,
                            size = PAGE_SIZE
                        )
                    }.onSuccess {
                        isWrite.value = true
                        _comments.value = it
                        _commentEmpty.value = it.content.isEmpty()
                    }
                    .onError { e -> catchError(e) }
            }
            dismissLoading()
        }
    }

    fun nextPage(postId: Long) {
        baseViewModelScope.launch {
            if (!comments.value.hasNext) {
                return@launch
            }
            commentsFromPostUserUseCase(
                postId = postId,
                page = comments.value.page + 1,
                size = PAGE_SIZE
            ).onSuccess { _comments.value = it
            }.onError { e -> catchError(e) }
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
            deletePostUseCase.invoke(postId)
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
