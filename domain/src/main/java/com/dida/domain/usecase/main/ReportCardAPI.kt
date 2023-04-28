package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class ReportCardAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(cardId: Long, content: String) : NetworkResult<Unit> {
        return repository.postReportCard(cardId = cardId, content = content)
    }
}
