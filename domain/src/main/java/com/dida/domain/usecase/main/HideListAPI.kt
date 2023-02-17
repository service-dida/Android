package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.nav.hide.CardHideList
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class HideListAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke() : NetworkResult<List<CardHideList>> {
        return repository.getHideList()
    }
}