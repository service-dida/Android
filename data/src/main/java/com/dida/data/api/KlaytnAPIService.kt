package com.dida.data.api

import com.dida.data.model.klaytn.AssetResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url

interface KlaytnAPIService {

    @Multipart
    @POST
    suspend fun uploadAsset(@Part file: MultipartBody.Part,
                            @Url url: String): AssetResponse
}