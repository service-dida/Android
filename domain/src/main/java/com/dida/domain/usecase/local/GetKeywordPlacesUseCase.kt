package com.dida.domain.usecase.local

import com.dida.domain.NetworkResult
import com.dida.domain.main.LocalRepository
import javax.inject.Inject

class GetKeywordPlacesUseCase @Inject constructor(
    private val repository: LocalRepository
) {
    suspend operator fun invoke() : NetworkResult<List<String>> {
        return repository.getKeywordPlaces()
    }
}
