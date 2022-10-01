package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import okhttp3.MultipartBody
import javax.inject.Inject


class UpdateProfileAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(description: MultipartBody.Part, file : MultipartBody.Part) : NetworkResult<Unit> {
        return repository.updateProfileAPI(description, file)
    }
}