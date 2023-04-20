package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class ReportUserAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(userId: Long, content: String) : NetworkResult<Unit> {
        return repository.postReportUser(userId = userId, content = content)
    }
}
