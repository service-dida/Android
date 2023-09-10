package com.dida.data.model.profile

import com.dida.domain.main.model.CommonProfile
import com.dida.domain.main.model.MemberInfo

fun GetCommonProfileResponse.toDomain(): CommonProfile {
    return CommonProfile(
        memberInfo.toDomain(), description, nftCnt, followerCnt, followingCnt
    )
}

fun MemberInfoResponse.toDomain(): MemberInfo {
    return MemberInfo(
        memberId, memberName, profileImgUrl
    )
}
