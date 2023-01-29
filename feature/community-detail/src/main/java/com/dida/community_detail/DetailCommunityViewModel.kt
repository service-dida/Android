package com.dida.community_detail

import com.dida.common.base.BaseViewModel
import com.dida.common.util.UiState
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.flatMap
import com.dida.domain.model.nav.post.Comments
import com.dida.domain.model.nav.post.Post
import com.dida.domain.onError
import com.dida.domain.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class DetailCommunityViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl
) : BaseViewModel() {

    private val TAG = "DetailCommunityViewModel"

    var postId: Int = 0

    private val _postState: MutableStateFlow<Post?> = MutableStateFlow(null)
    val postState: StateFlow<Post?> = _postState.asStateFlow()

    private val _commentList: MutableStateFlow<List<Comments>> = MutableStateFlow(emptyList())
    val commentList: StateFlow<List<Comments>> = _commentList.asStateFlow()

    fun getPost(postId: Int) {
        baseViewModelScope.launch {
            showLoading()
            mainRepositoryImpl.getPostPostId(postId = postId)
                .onSuccess { _postState.value = it }
                .flatMap { mainRepositoryImpl.getPostIdComments(postId = postId) }
                .onSuccess { _commentList.value = it }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }
}
