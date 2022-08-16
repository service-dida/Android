package com.dida.data.model.createwallet

import com.google.gson.annotations.SerializedName

data class PostCheckPasswordResponse(
    @SerializedName("flag") val flag: Boolean
)