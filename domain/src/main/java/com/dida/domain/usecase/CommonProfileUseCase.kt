package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.CommonProfile
import javax.inject.Inject

class CommonProfileUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke() : NetworkResult<CommonProfile> {
        return repository.commonProfile()
    }
}
