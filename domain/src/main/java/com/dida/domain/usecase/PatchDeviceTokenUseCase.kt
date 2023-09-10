package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class PatchDeviceTokenUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(deviceToken: String) : NetworkResult<Unit> {
        return repository.patchDeviceToken(deviceToken = deviceToken)
    }
}
