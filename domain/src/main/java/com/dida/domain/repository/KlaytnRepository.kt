package com.dida.domain.repository

import com.dida.domain.NetworkResult
import com.dida.domain.model.klaytn.Asset
import okhttp3.MultipartBody

interface KlaytnRepository {
    suspend fun uploadAsset(file: MultipartBody.Part) : NetworkResult<Asset>
}