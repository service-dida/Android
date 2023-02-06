package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetWalletExistsResponse(
    @SerializedName("existed") val existed: Boolean
)
