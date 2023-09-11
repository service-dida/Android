package com.dida.domain.usecase

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.CommonProfileNft
import com.dida.domain.main.model.LoginToken
import javax.inject.Inject

class GetCommonProfileNftUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int, sort: String) : NetworkResult<Contents<CommonProfileNft>> {
        return repository.commonProfileNft(page, pageSize, sort)
    }
}
