package com.dida.data.model.login

import com.google.gson.annotations.SerializedName

data class GetCommonWalletResponse(
    @SerializedName("existed") val existed: Boolean
)
