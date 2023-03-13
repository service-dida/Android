package com.dida.data.model.request

import com.google.gson.annotations.SerializedName

data class PatchNftRemoveRequest(
    @SerializedName("cardId") val cardId: Long,
    @SerializedName("payPwd") val payPwd: String
)
