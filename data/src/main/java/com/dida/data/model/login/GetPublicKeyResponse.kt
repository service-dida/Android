package com.dida.data.model.login

import com.google.gson.annotations.SerializedName

data class GetPublicKeyResponse(
    @SerializedName("publicKey") val publicKey: String
)
