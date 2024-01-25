package com.dida.data.model.profile

import com.google.gson.annotations.SerializedName

data class GetMemberProfileResponse(
    @SerializedName("memberDetailInfo") val memberDetailInfo: GetCommonProfileResponse,
    @SerializedName("followed") val followed: Boolean,
)
