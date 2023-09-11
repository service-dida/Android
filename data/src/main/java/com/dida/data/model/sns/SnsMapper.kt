package com.dida.data.model.sns

import com.dida.domain.Contents
import com.dida.domain.main.model.Post

fun GetPostsResponse.toDomain(): Contents<Post> {
    return Contents(
        page = page,
        pageSize = pageSize,
        hasNext = hasNext,
        content = response
    )
}

fun GetPostDetailResponse.toDomain(): Post {
    return Post(postInfo, memberInfo, nftInfo, type, createdAt, comments)
}
