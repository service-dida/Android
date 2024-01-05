package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.AppVersion
import javax.inject.Inject

class AppVersionUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(versionId: Long) : NetworkResult<AppVersion> {
        return repository.checkVersion(versionId = versionId)
    }
}
