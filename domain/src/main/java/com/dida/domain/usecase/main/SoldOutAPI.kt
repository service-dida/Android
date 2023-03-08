package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.SoldOut
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class SoldOutAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(term: Int) : NetworkResult<List<SoldOut>> {
        return repository.getSoldOutAPI(term)
    }
}