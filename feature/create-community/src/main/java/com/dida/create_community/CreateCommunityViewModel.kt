package com.dida.create_community

import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGE_SIZE
import com.dida.domain.Contents
import com.dida.domain.flatMap
import com.dida.domain.main.model.OwnNft
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.LikedNftsUseCase
import com.dida.domain.usecase.OwnNftsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCommunityViewModel @Inject constructor(
    private val ownNftsUseCase: OwnNftsUseCase,
    private val likedNftsUseCase: LikedNftsUseCase
) : BaseViewModel(), CreateCommunityActionHandler {

    private val TAG = "CreateCommunityViewModel"

    private val _navigationEvent: MutableSharedFlow<CreateCommunityNavigationAction> = MutableSharedFlow<CreateCommunityNavigationAction>()
    val navigationEvent: SharedFlow<CreateCommunityNavigationAction> = _navigationEvent

    private val _ownNftState: MutableStateFlow<Contents<OwnNft>> =
        MutableStateFlow<Contents<OwnNft>>(
            Contents(page = 0, pageSize = 0, hasNext = true, content = emptyList())
        )
    val ownNftState: StateFlow<Contents<OwnNft>> = _ownNftState.asStateFlow()

    private val _likeNftState: MutableStateFlow<Contents<OwnNft>> =
        MutableStateFlow<Contents<OwnNft>>(
            Contents(page = 0, pageSize = 0, hasNext = true, content = emptyList())
        )
    val likeNftState: StateFlow<Contents<OwnNft>> = _likeNftState.asStateFlow()

    init {
        baseViewModelScope.launch {
            likedNftsUseCase.invoke(0, PAGE_SIZE)
                .onSuccess { _likeNftState.value = it }
                .flatMap { ownNftsUseCase(0, PAGE_SIZE) }
                .onSuccess { _ownNftState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    override fun onNftSelectClicked(cardId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CreateCommunityNavigationAction.NavigateToSelectNft(cardId = cardId))
        }
    }

    override fun onLikeButtonClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(CreateCommunityNavigationAction.NavigateToLike)
        }
    }

    override fun onCreateButtonClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(CreateCommunityNavigationAction.NavigateToCreate)
        }
    }

    fun onNextPageFromLikeNft() {
        baseViewModelScope.launch {
            if (!likeNftState.value.hasNext) {
                return@launch
            }
            likedNftsUseCase(page = likeNftState.value.page + 1, PAGE_SIZE).onSuccess {
                _likeNftState.value = it.copy(content = it.content)
            }
        }
    }

    fun onNextPageFromOwnNft() {
        baseViewModelScope.launch {
            if (!ownNftState.value.hasNext) {
                return@launch
            }
            ownNftsUseCase(page = ownNftState.value.page + 1, PAGE_SIZE).onSuccess {
                _ownNftState.value = it.copy(content = ownNftState.value.content + it.content)
            }
        }
    }
}
