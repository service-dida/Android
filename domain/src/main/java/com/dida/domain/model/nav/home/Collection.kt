package com.dida.domain.model.nav.home

import com.google.gson.annotations.SerializedName

class Collection(
    @SerializedName("userId") val userId: Int,
    @SerializedName("userImg") var userImg: String,
    @SerializedName("userName") var userName: String,
    @SerializedName("userDetail") var userDetail: String,
    @SerializedName("follow") var follow: Boolean
)