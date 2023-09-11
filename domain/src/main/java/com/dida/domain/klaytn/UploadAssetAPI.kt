package com.dida.domain.klaytn

import com.dida.domain.NetworkResult
import com.dida.domain.klaytn.Asset
import com.dida.domain.klaytn.KlaytnRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadAssetAPI @Inject constructor(
    private val repository: KlaytnRepository
){
    suspend operator fun invoke(file: MultipartBody.Part) : NetworkResult<Asset> {
        return repository.uploadAsset(file)
    }
}
