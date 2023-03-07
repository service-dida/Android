package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.nav.community.HotCard
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class HotCardAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke() : NetworkResult<List<HotCard>> {
        return repository.getHotCards()
    }
}
