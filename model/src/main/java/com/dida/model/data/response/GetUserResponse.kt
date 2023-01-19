package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    @SerializedName("userId") val userId: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("getWallet") val getWallet: Boolean,
    @SerializedName("cardCnt") val cardCnt: Int,
    @SerializedName("followerCnt") val followerCnt: Int,
    @SerializedName("followingCnt") val followingCnt: Int
)
