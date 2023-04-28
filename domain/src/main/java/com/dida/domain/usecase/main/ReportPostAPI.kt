package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class ReportPostAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(postId: Long, content: String) : NetworkResult<Unit> {
        return repository.postReportPost(postId = postId, content = content)
    }
}
