package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class PatchProfileDescriptionUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(description: String) : NetworkResult<Unit> {
        return repository.patchProfileDescription(description)
    }
}
