package com.dida.data.model.additional

import com.google.gson.annotations.SerializedName

data class PostMakePictureRequest(
    @SerializedName("payPwd") val payPwd: String,
    @SerializedName("sentence") val sentence: String,
)
