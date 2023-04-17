package com.dida.community_detail

import com.dida.common.actionhandler.CommentActionHandler
import com.dida.common.actionhandler.ReportActionHandler
import com.dida.common.actionhandler.UpdateActionHandler
import com.dida.common.ballon.UpdateBalloon
import com.dida.common.base.BaseViewModel
import com.dida.data.DataApplication
import com.dida.domain.flatMap
import com.dida.domain.model.main.Comments
import com.dida.domain.model.main.Post
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCommunityViewModel @Inject constructor(
    private val postIdAPI: PostIdAPI,
    private val commentsPostIdAPI: CommentsPostIdAPI,
    private val commentAPI: CommentAPI,
    private val deleteCommentAPI: DeleteCommentAPI,
    private val deletePostAPI: DeletePostAPI
) : BaseViewModel(), DetailCommunityActionHandler, CommentActionHandler, ReportActionHandler, UpdateActionHandler {

    private val TAG = "DetailCommunityViewModel"

    private val _navigationEvent: MutableSharedFlow<DetailCommunityNavigationAction> = MutableSharedFlow<DetailCommunityNavigationAction>()
    val navigationEvent: SharedFlow<DetailCommunityNavigationAction> = _navigationEvent.asSharedFlow()

    private val _messageEvent: MutableSharedFlow<DetailCommunityMessageAction> = MutableSharedFlow<DetailCommunityMessageAction>()
    val messageEvent: SharedFlow<DetailCommunityMessageAction> = _messageEvent.asSharedFlow()

    private val _postState: MutableStateFlow<Post?> = MutableStateFlow(null)
    val postState: StateFlow<Post?> = _postState.asStateFlow()

    private val _commentList: MutableStateFlow<List<Comments>> = MutableStateFlow(emptyList())
    val commentList: StateFlow<List<Comments>> = _commentList.asStateFlow()

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
                    isWrite.value = false }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    fun deleteComment(commentId: Long) {
        baseViewModelScope.launch {
            deleteCommentAPI(commentId = commentId)
                .onSuccess {
                    _messageEvent.emit(DetailCommunityMessageAction.DeletePostMessage)
                    getPost(postId = postState.value!!.postId) }
                .onError { e -> catchError(e) }
        }
    }

    override fun onCommentClicked() {
        baseViewModelScope.launch {
            if(commentState.value.isNotBlank()) {
                commentAPI(postId = postState.value!!.postId, content = commentState.value)
                    .onSuccess { commentState.value = "" }
                    .flatMap { commentsPostIdAPI.invoke(postId = postState.value!!.postId) }
                    .onSuccess {
                        isWrite.value = true
                        _commentList.value = it }
                    .onError { e -> catchError(e) }
            }
        }
    }

    override fun onCommunityMoreClicked(userId: Long) {
        baseViewModelScope.launch {
            if (DataApplication.dataStorePreferences.getUserId() != userId) {
                _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToCommunityMore(userId))
            } else {
                _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToMyMore)
            }
        }
    }

    override fun onCommentMoreClicked(commentId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToCommentMore(commentId = commentId))
        }
    }

    override fun onCommentUserProfileClicked(userId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToUserProfile(userId = userId))
        }
    }

    override fun onUserProfileClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToUserProfile(userId = postState.value!!.userId))
        }
    }

    override fun onCardClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToCardDetail(cardId = postState.value!!.cardId))
        }
    }

    override fun onReportClicked(userId: Long) {
    }

    override fun onBlockClicked(userId: Long) {
    }

    override fun onUpdateClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToUpdateCommunity(postId = postState.value!!.postId))
        }
    }

    fun onDeletePost() {
        baseViewModelScope.launch {
            showLoading()
            deletePostAPI.invoke(postState.value!!.postId)
                .onSuccess {
                    _messageEvent.emit(DetailCommunityMessageAction.DeletePostMessage)
                    _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToBack) }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    override fun onDeleteClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(DetailCommunityNavigationAction.NavigateToDelete)
        }
    }
}
