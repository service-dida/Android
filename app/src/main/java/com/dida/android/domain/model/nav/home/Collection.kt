package com.dida.android.domain.model.nav.home

import com.google.gson.annotations.SerializedName

class Collection(
    @SerializedName("userImg") var userImg: String,
    @SerializedName("userName") var userName: String,
    @SerializedName("userDetail") var userDetail: String,
    @SerializedName("follow") var follow: Boolean
)