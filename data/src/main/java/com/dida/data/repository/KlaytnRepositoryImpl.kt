package com.dida.data.repository

import com.dida.data.api.KlaytnAPIService
import com.dida.data.api.handleApi
import com.dida.data.mapper.toDomain
import com.dida.data.model.klaytn.MetaDataRequest
import com.dida.domain.NetworkResult
import com.dida.domain.model.klaytn.Asset
import com.dida.domain.model.klaytn.MetaData
import com.dida.domain.usecase.KlaytnUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Named


class KlaytnRepositoryImpl @Inject constructor(@Named("Klaytn") private val klaytnAPIService: KlaytnAPIService) :  KlaytnUsecase {

    override suspend fun uploadAsset(file: MultipartBody.Part): NetworkResult<Flow<Asset>> {
        return handleApi {
            flow {
                emit(klaytnAPIService.uploadAsset(file).toDomain())
            }.flowOn(Dispatchers.IO)
        }
    }

    override suspend fun uploadMetaData(name: String, description: String, image: String): NetworkResult<Flow<MetaData>> {
        val request = MetaDataRequest(com.dida.data.model.klaytn.MetaData(name,description,image))
        return handleApi {
            flow {
                emit(klaytnAPIService.uploadMetaData(request).toDomain())
            }.flowOn(Dispatchers.IO)
        }
    }
}


