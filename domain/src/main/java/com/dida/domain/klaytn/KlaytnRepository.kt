package com.dida.domain.klaytn

import com.dida.domain.NetworkResult
import com.dida.domain.klaytn.Asset
import okhttp3.MultipartBody

interface KlaytnRepository {
    suspend fun uploadAsset(file: MultipartBody.Part) : NetworkResult<Asset>
}
