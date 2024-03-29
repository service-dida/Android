package com.dida.data.model.additional

import com.dida.domain.Contents
import com.dida.domain.main.model.AiPicture
import com.dida.domain.main.model.Alarm
import com.dida.domain.main.model.AppVersion
import com.dida.domain.main.model.HideMember
import com.dida.domain.main.model.HideNft
import com.dida.domain.main.model.Keywords

fun PostMakePictureResponse.toDomain(): AiPicture {
    return AiPicture(url1, url2, url3, url4)
}

fun GetAlarmsResponse.toDomain(): Contents<Alarm> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}

fun GetHideNftsResponse.toDomain(): Contents<HideNft> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}

fun GetHideMembersResponse.toDomain(): Contents<HideMember> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}

fun GetKeywordsResponse.toDomain(): Keywords {
    val response = this
    return Keywords(
        things = response.things,
        places = response.places
    )
}

fun GetAppVersionResponse.toDomain(): AppVersion {
    val response = this
    return AppVersion(
        versionId = response.versionId,
        version = response.version,
        changes = response.changes,
        essentialUpdate = response.essentialUpdate
    )
}
