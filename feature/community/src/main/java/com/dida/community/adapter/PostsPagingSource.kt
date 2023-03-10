package com.dida.community.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dida.domain.NetworkResult
import com.dida.domain.fold
import com.dida.domain.model.main.Posts
import com.dida.domain.usecase.main.PostsAPI

fun createPostsPager(
    postsAPI: PostsAPI
): Pager<Int, Posts> = Pager(
    config = PagingConfig(pageSize = 20, initialLoadSize = 20, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = { PostsPagingSource(postsAPI = postsAPI) }
)

class PostsPagingSource(
    private val postsAPI: PostsAPI
) : PagingSource<Int, Posts>() {

    override fun getRefreshKey(state: PagingState<Int, Posts>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Posts> {
        val pageIndex = params.key ?: 0
        val result = postsAPI.invoke(page = pageIndex)

        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents,
                    prevKey = if (pageIndex == 0) null else pageIndex - 1,
                    nextKey = if (contents.isNotEmpty()) pageIndex + 1 else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }

}
