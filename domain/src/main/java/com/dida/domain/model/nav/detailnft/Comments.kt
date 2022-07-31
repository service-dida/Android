package com.dida.domain.model.nav.detailnft

import com.google.gson.annotations.SerializedName

class Comments(
    @SerializedName("userImg") var userImg: String,
    @SerializedName("contents") var contents: String
)