package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.Report
import javax.inject.Inject

class ReportUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(type: Report, reportedId: Long, description: String) : NetworkResult<Unit> {
        return repository.report(type, reportedId, description)
    }
}
