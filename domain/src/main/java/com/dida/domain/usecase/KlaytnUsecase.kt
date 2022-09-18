package com.dida.domain.usecase

import com.dida.domain.NetworkResult
import com.dida.domain.model.klaytn.Asset
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface KlaytnUsecase {
    suspend fun uploadAsset(file: MultipartBody.Part) : NetworkResult<Flow<Asset>>
}