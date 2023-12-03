package com.dida.domain.usecase.local

import com.dida.domain.NetworkResult
import com.dida.domain.main.LocalRepository
import com.dida.domain.main.model.Keywords
import javax.inject.Inject

class SetKeywordsUseCase @Inject constructor(
    private val repository: LocalRepository
) {
    suspend operator fun invoke(keywords: Keywords) : NetworkResult<Unit> {
        return repository.setKeywords(keywords)
    }
}
