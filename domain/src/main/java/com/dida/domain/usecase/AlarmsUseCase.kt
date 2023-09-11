package com.dida.domain.usecase

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.Alarm
import javax.inject.Inject

class AlarmsUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(page: Int, size: Int) : NetworkResult<Contents<Alarm>> {
        return repository.alarms(page, size)
    }
}
