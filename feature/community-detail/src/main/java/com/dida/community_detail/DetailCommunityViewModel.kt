package com.dida.community_detail

import com.dida.common.base.BaseViewModel
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.flatMap
import com.dida.domain.model.nav.post.Comments
import com.dida.domain.model.nav.post.Post
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CommentAPI
import com.dida.domain.usecase.main.CommentsPostIdAPI
import com.dida.domain.usecase.main.PostIdAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCommunityViewModel @Inject constructor(
    private val postIdAPI: PostIdAPI,
    private val commentsPostIdAPI: CommentsPostIdAPI,
    private val commentAPI: CommentAPI
) : BaseViewModel(), DetailCommunityActionHandler {

    private val TAG = "DetailCommunityViewModel"

    private val _postState: MutableStateFlow<Post?> = MutableStateFlow(null)
    val postState: StateFlow<Post?> = _postState.asStateFlow()

    private val _commentList: MutableStateFlow<List<Comments>> = MutableStateFlow(emptyList())
    val commentList: StateFlow<List<Comments>> = _commentList.asStateFlow()

    val commentState: MutableStateFlow<String> = MutableStateFlow("")

    fun getPost(postId: Int) {
        baseViewModelScope.launch {
            showLoading()
            postIdAPI.invoke(postId = postId)
                .onSuccess { _postState.value = it }
                .flatMap { commentsPostIdAPI.invoke(postId = postId) }
                .onSuccess { _commentList.value = it }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

    override fun onCommentClicked() {
        baseViewModelScope.launch {
            if(commentState.value.isNotBlank()) {
                commentAPI(postId = postState.value!!.postId, content = commentState.value)
                    .onSuccess {
                        commentState.value = ""
                        getPost(postId = postState.value!!.postId.toInt()) }
                    .onError { e -> catchError(e) }
            }
        }
    }
}
