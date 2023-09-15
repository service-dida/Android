package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import javax.inject.Inject

class CreateNftUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(payPwd: String, title: String, description: String, image: String) : NetworkResult<Unit> {
        return repository.createNft(payPwd, title, description, image)
    }
}
