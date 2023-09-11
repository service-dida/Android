package com.dida.data.model.additional

import com.dida.domain.main.model.AiPicture

fun PostMakePictureResponse.toDomain(): AiPicture {
    return AiPicture(url1, url2, url3, url4)
}
