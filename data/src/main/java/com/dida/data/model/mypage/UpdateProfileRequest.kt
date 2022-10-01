package com.dida.data.model.mypage

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Part

data class UpdateProfileRequest(
    @SerializedName("description") @Body val description: String,
    @SerializedName("file") @Part val file: MultipartBody.Part
)
