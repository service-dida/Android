package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class GetUserWalletResponse(
    @SerializedName("existed") val existed: Boolean
)
