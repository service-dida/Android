package com.dida.domain.usecase

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.HotSellerPage
import com.dida.domain.main.model.SoldOut
import javax.inject.Inject

class HotSellerPageUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(page: Int, size: Int) : NetworkResult<Contents<HotSellerPage>> {
        return repository.hotSellerPage(page, size)
    }
}
