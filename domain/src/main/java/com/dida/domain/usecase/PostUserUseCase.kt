package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.LoginToken
import javax.inject.Inject

class PostUserUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(email: String, nickname: String) : NetworkResult<LoginToken> {
        return repository.postUser(email = email, nickname = nickname)
    }
}
