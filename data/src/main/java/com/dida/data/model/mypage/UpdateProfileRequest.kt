package com.dida.data.model.mypage

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class UpdateProfileRequest(
    @SerializedName("description") val description: String,
    @SerializedName("file") val file: MultipartBody.Part
)
