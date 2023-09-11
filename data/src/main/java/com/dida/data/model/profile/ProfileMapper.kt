package com.dida.data.model.profile

import com.dida.domain.main.model.CommonProfile
import com.dida.domain.main.model.CommonProfileNft
import com.dida.domain.Contents
import com.dida.domain.main.model.CommonFollow
import com.dida.domain.main.model.MemberInfo
import com.dida.domain.main.model.MemberProfile

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

fun GetCommonProfileNftResponse.toDomain(): Contents<CommonProfileNft> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}

fun GetMemberProfileResponse.toDomain(): MemberProfile {
    return MemberProfile(
        memberDetailInfo = memberDetailInfo.toDomain(),
        followed = followed
    )
}

fun GetCommonFollowResponse.toDomain(): Contents<CommonFollow> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}
