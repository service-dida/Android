package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.nav.post.CardPost
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class CardsPostLikeAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke() : NetworkResult<List<CardPost>> {
        return repository.getCardsPostLike()
    }
}
