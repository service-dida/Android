package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.Keywords
import javax.inject.Inject

class GetKeywordsUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke() : NetworkResult<Keywords> {
        return repository.getKeywords()
    }
}
