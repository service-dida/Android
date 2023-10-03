package com.dida.domain.usecase

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.RecentNft
import okhttp3.MultipartBody
import javax.inject.Inject

class RecentNftsUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(page: Int, size: Int) : NetworkResult<Contents<RecentNft>> {
        return repository.recentNfts(page, size)
    }
}
