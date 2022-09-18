package com.dida.data.api

import com.dida.data.model.klaytn.AssetResponse
import com.dida.data.model.klaytn.MetaDataRequest
import com.dida.data.model.klaytn.MetaDataResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface KlaytnAPIService {

    @Multipart
    @POST
    suspend fun uploadAsset(@Part file: MultipartBody.Part,
                            @Url url: String = "https://metadata-api.klaytnapi.com/v1/metadata/asset"): AssetResponse


    @POST
    suspend fun uploadMetaData(
        @Body metadata: MetaDataRequest,
        @Url url: String = "https://metadata-api.klaytnapi.com/v1/metadata",
        @Header("Content-Type") contentType: String = "application/json"
    ): MetaDataResponse
}