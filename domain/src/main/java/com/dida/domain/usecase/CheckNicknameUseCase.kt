package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class CheckNicknameUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(nickname: String) : NetworkResult<Boolean> {
        return repository.checkNickname(nickname = nickname)
    }
}
