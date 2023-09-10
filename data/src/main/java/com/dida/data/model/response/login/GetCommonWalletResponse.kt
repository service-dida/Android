package com.dida.data.model.response.login

import com.google.gson.annotations.SerializedName

data class GetCommonWalletResponse(
    @SerializedName("existed") val existed: Boolean
)
