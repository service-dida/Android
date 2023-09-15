package com.dida.domain.usecase

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.SoldOut
import javax.inject.Inject

class SoldOutPageUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(range: Int, page: Int, size: Int) : NetworkResult<Contents<SoldOut>> {
        return repository.soldOutPage(range, page, size)
    }
}
