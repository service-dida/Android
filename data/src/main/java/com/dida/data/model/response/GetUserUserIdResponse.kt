package com.dida.data.model.response

import com.google.gson.annotations.SerializedName

data class GetUserUserIdResponse(
    @SerializedName("userId") val userId: Long,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("cardCnt") val cardCnt: Int,
    @SerializedName("followerCnt") val followerCnt: Int,
    @SerializedName("followingCnt") val followingCnt: Int,
    @SerializedName("followed") val followed: Boolean
)
