package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class NicknameCheckAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(nickName: String): NetworkResult<NicknameResponseModel> {
        return repository.nicknameAPI(nickName)
    }
}