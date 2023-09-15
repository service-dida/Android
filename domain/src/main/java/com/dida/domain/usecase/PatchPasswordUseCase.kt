package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class PatchPasswordUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(nowPwd: String, changePwd: String) : NetworkResult<Unit> {
        return repository.patchPassword(nowPwd, changePwd)
    }
}
