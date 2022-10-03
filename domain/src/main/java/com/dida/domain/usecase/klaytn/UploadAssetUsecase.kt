package com.dida.domain.usecase.klaytn

import com.dida.domain.NetworkResult
import com.dida.domain.model.klaytn.Asset
import com.dida.domain.repository.KlaytnRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadAssetUsecase @Inject constructor(
    private val repository: KlaytnRepository
){
    suspend operator fun invoke(file: MultipartBody.Part) : NetworkResult<Asset> {
        return repository.uploadAsset(file)
    }
}