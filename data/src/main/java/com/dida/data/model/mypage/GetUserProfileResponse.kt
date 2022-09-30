package com.dida.data.model.mypage

import com.google.gson.annotations.SerializedName

data class GetUserProfileResponse(
    @SerializedName("cardCnt") val cardCnt: Int,
    @SerializedName("description") val description: String,
    @SerializedName("followerCnt") val followerCnt: Int,
    @SerializedName("followingCnt") val followingCnt: Int,
    @SerializedName("getWallet") val getWallet: Boolean,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("userId") val userId: Int
)