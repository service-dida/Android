package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.LoginToken
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke() : NetworkResult<Unit> {
        return repository.deleteUser()
    }
}
