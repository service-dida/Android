package com.dida.data.klaytn

import com.dida.data.model.klatytn.AssetResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url

interface KlaytnApi {

    @Multipart
    @POST
    suspend fun uploadAsset(
        @Part file: MultipartBody.Part,
        @Url url: String = "https://metadata-api.klaytnapi.com/v1/metadata/asset"
    ): AssetResponse
}
