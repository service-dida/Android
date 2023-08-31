package com.dida.domain.usecase.main

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.CommonAlarm
import com.dida.domain.repository.MainRepository
import javax.inject.Inject

class NotificationListAPI @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(page: Int, size: Int) : NetworkResult<List<CommonAlarm>> {
        return repository.getCommonAlarm(page = page, size = size)
    }
}
