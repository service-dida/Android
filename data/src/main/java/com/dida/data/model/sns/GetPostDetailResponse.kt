package com.dida.data.model.sns

import com.dida.domain.main.model.Comment
import com.dida.domain.main.model.MemberInfo
import com.dida.domain.main.model.NftInfo
import com.dida.domain.main.model.Post
import com.dida.domain.main.model.PostInfo
import com.google.gson.annotations.SerializedName

data class GetPostDetailResponse(
    @SerializedName("postInfo") val postInfo: PostInfo,
    @SerializedName("memberInfo") val memberInfo: MemberInfo,
    @SerializedName("nftInfo") val nftInfo: NftInfo,
    @SerializedName("type") val type: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("comments") val comments: List<Comment>,
)
