package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.repository.MainRepository
import javax.inject.Inject


class DeleteUserHideAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(userId : Long) : NetworkResult<Unit> {
        return repository.deleteUserHide(userId)
    }
}
