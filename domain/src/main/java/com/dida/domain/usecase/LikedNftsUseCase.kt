package com.dida.domain.usecase

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.LoginToken
import com.dida.domain.main.model.OwnNft
import javax.inject.Inject

class LikedNftsUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(page: Int, size: Int) : NetworkResult<Contents<OwnNft>> {
        return repository.likedNfts(page, size)
    }
}
