package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.HotUser
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class HotUserAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(page: Int) : NetworkResult<List<HotUser>> {
        return repository.getHotUser(page = page)
    }
}
