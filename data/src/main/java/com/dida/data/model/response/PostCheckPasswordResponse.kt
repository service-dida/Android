package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class PostCheckPasswordResponse(
    @SerializedName("flag") val flag: Boolean,
    @SerializedName("wrongCount") val wrongCount: Int

)
