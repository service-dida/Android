package com.dida.data.model.profile

import com.google.gson.annotations.SerializedName

data class GetCommonProfileResponse(
    @SerializedName("memberInfo") val memberInfo: MemberInfoResponse,
    @SerializedName("description") val description: String?,
    @SerializedName("nftCnt") val nftCnt: Long,
    @SerializedName("followerCnt") val followerCnt: Long,
    @SerializedName("followingCnt") val followingCnt: Long,
)

data class MemberInfoResponse(
    @SerializedName("memberId") val memberId: Long,
    @SerializedName("memberName") val memberName: String,
    @SerializedName("profileImgUrl") val profileImgUrl: String?,
)
