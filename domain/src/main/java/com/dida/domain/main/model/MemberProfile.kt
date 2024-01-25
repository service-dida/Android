package com.dida.domain.main.model

data class MemberProfile(
    val memberDetailInfo: CommonProfile,
    val followed: Boolean,
)
