package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class PostReportCommentAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(reportedId: Long, content: String) : NetworkResult<Unit> {
        return repository.postReportComment(reportedId,content)
    }
}