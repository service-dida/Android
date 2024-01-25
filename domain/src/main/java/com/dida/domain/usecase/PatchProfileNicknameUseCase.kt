package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class PatchProfileNicknameUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(nickname: String) : NetworkResult<Unit> {
        return repository.patchProfileNickname(nickname)
    }
}
