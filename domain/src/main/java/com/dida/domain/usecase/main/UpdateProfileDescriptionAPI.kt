package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class UpdateProfileDescriptionAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(description: String) : NetworkResult<Unit> {
        return repository.updateProfileDescriptionAPI(description)
    }
}
