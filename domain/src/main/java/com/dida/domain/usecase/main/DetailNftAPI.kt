package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.DetailNft
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class DetailNftAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(cardId : Long) : NetworkResult<DetailNft> {
        return repository.getDetailNFT(cardId)
    }
}