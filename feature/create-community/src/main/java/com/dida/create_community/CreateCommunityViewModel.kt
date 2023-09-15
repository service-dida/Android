package com.dida.create_community

import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGING_SIZE
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

    private val _cardPostMyState: MutableStateFlow<Contents<OwnNft>> =
        MutableStateFlow<Contents<OwnNft>>(
            Contents(page = 0, pageSize = 0, hasNext = true, content = emptyList())
        )
    val cardPostMyState: StateFlow<Contents<OwnNft>> = _cardPostMyState.asStateFlow()

    private val _cardPostLikeState: MutableStateFlow<Contents<OwnNft>> =
        MutableStateFlow<Contents<OwnNft>>(
            Contents(page = 0, pageSize = 0, hasNext = true, content = emptyList())
        )
    val cardPostLikeState: StateFlow<Contents<OwnNft>> = _cardPostMyState.asStateFlow()

    private var likedPage: Int = 0
    private var ownPage: Int = 0

    init {
        baseViewModelScope.launch {
            likedNftsUseCase.invoke(likedPage, PAGING_SIZE)
                .onSuccess { _cardPostLikeState.value = it }
                .flatMap { ownNftsUseCase(ownPage, PAGING_SIZE) }
                .onSuccess { _cardPostMyState.value = it }
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
            _navigationEvent.emit(CreateCommunityNavigationAction.NavigateToCreate)
        }
    }

    override fun onCreateButtonClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(CreateCommunityNavigationAction.NavigateToLike)
        }
    }

    fun nextPage(nftState: Int) {
        baseViewModelScope.launch {
            showLoading()
            if (nftState == 0) {
                if (!cardPostLikeState.value.hasNext) {
                    dismissLoading()
                    return@launch
                }
                likedPage += 1
                likedNftsUseCase(page = likedPage, PAGING_SIZE).onSuccess {
                    _cardPostLikeState.value = it.copy(content = cardPostLikeState.value.content + it.content)
                }
            } else {
                if (!cardPostMyState.value.hasNext) {
                    dismissLoading()
                    return@launch
                }
                ownPage += 1
                ownNftsUseCase(page = ownPage, PAGING_SIZE).onSuccess {
                    _cardPostMyState.value = it.copy(content = cardPostMyState.value.content + it.content)
                }
            }
            dismissLoading()
        }
    }
}
