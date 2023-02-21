package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class MintNftAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(payPwd: String, name : String, description : String, image : String) : NetworkResult<Long> {
        return repository.mintNFT(payPwd, name, description, image)
    }
}