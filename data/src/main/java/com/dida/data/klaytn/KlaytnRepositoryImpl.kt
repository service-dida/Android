package com.dida.data.klaytn

import com.dida.data.api.handleApi
import com.dida.data.mapper.toDomain
import com.dida.domain.NetworkResult
import com.dida.domain.klaytn.Asset
import com.dida.domain.klaytn.KlaytnRepository
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Named

class KlaytnRepositoryImpl @Inject constructor(
    @Named("Klaytn") private val klaytnRemoteService: KlaytnRemoteService
) : KlaytnRepository {

    override suspend fun uploadAsset(file: MultipartBody.Part): NetworkResult<Asset> {
        return handleApi { klaytnRemoteService.uploadAsset(file).toDomain() }
    }
}


