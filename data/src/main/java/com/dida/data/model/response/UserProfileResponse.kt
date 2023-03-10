package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("cardCnt") val cardCnt: Int,
    @SerializedName("description") val description: String,
    @SerializedName("followerCnt") val followerCnt: Int,
    @SerializedName("followingCnt") val followingCnt: Int,
    @SerializedName("getWallet") val getWallet: Boolean,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("userId") val userId: Long
)
