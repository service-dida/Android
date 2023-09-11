package com.dida.data.klaytn

import com.dida.data.api.handleApi
import com.dida.data.model.klatytn.toDomain
import com.dida.domain.NetworkResult
import com.dida.domain.klaytn.Asset
import com.dida.domain.klaytn.KlaytnRepository
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Named

class KlaytnRepositoryImpl @Inject constructor(
    @Named("Klaytn") private val klaytnApi: KlaytnApi
) : KlaytnRepository {

    override suspend fun uploadAsset(file: MultipartBody.Part): NetworkResult<Asset> {
        return handleApi { klaytnApi.uploadAsset(file).toDomain() }
    }
}


