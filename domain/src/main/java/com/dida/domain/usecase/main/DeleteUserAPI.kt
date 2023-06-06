package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.TradeHistory
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class DeleteUserAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(): NetworkResult<Unit> {
        return repository.deleteMember()
    }
}
