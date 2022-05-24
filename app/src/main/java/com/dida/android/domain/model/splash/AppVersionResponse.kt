package com.dida.android.domain.model.splash

import com.google.gson.annotations.SerializedName

data class AppVersionResponse(
    @SerializedName("version")
    val version: Int
)