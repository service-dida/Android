package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class SendEmailAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke() : NetworkResult<RandomNumber> {
        return repository.getSendEmailAPI()
    }
}