package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.SoldOut
import javax.inject.Inject

class SoldOutUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(range: Int) : NetworkResult<List<SoldOut>> {
        return repository.soldOut(range)
    }
}
