package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import okhttp3.MultipartBody
import javax.inject.Inject


class UpdateProfileImageAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(file : MultipartBody.Part) : NetworkResult<Unit> {
        return repository.updateProfileImageAPI(file)
    }
}