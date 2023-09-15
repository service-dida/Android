package com.dida.domain.klaytn

import com.dida.domain.NetworkResult
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadAssetUseCase @Inject constructor(
    private val repository: KlaytnRepository
){
    suspend operator fun invoke(file: MultipartBody.Part) : NetworkResult<Asset> {
        return repository.uploadAsset(file)
    }
}
