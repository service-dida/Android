package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class PatchProfileImageUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(file: MultipartBody.Part) : NetworkResult<Unit> {
        return repository.patchProfileImage(file)
    }
}
