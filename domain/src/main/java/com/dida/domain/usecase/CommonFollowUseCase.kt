package com.dida.domain.usecase

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.CommonFollow
import javax.inject.Inject

class CommonFollowUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int) : NetworkResult<Contents<CommonFollow>> {
        return repository.commonFollow(page, pageSize)
    }
}
