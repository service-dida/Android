package com.dida.create_community

import com.dida.common.base.BaseViewModel
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.nav.post.CardPost
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.CardsPostLikeAPI
import com.dida.domain.usecase.main.CardsPostMyAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCommunityViewModel @Inject constructor(
    private val cardsPostLikeAPI: CardsPostLikeAPI,
    private val cardsPostMyAPI: CardsPostMyAPI,
) : BaseViewModel(), CreateCommunityActionHandler {

    private val TAG = "CreateCommunityViewModel"

    private val _navigationEvent: MutableSharedFlow<CreateCommunityNavigationAction> = MutableSharedFlow<CreateCommunityNavigationAction>()
    val navigationEvent: SharedFlow<CreateCommunityNavigationAction> = _navigationEvent

    private val _cardPostMyState: MutableStateFlow<List<CardPost>> = MutableStateFlow<List<CardPost>>(emptyList())
    val cardPostMyState: StateFlow<List<CardPost>> = _cardPostMyState.asStateFlow()

    private val _cardPostLikeState: MutableStateFlow<List<CardPost>> = MutableStateFlow<List<CardPost>>(emptyList())
    val cardPostLikeState: StateFlow<List<CardPost>> = _cardPostLikeState.asStateFlow()

    override fun onNftSelectClicked(cardId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(CreateCommunityNavigationAction.NavigateToSelectNft(cardId = cardId))
        }
    }

    fun getCardsPostMy() {
        baseViewModelScope.launch {
            cardsPostMyAPI.invoke()
                .onSuccess { _cardPostMyState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    fun getCardsPostLike() {
        baseViewModelScope.launch {
            cardsPostLikeAPI.invoke()
                .onSuccess { _cardPostLikeState.value = it }
                .onError { e -> catchError(e) }
        }
    }
}
