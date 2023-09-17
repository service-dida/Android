package com.dida.community.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dida.domain.fold
import com.dida.domain.main.model.Post
import com.dida.domain.usecase.PostsUseCase

fun createPostsPager(
    postsUseCase: PostsUseCase
): Pager<Int, Post> = Pager(
    config = PagingConfig(pageSize = 20, initialLoadSize = 20, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = { PostsPagingSource(postsUseCase) }
)

class PostsPagingSource(
    private val postsUseCase: PostsUseCase
) : PagingSource<Int, Post>() {

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val pageIndex = params.key ?: 0
        val result = postsUseCase.invoke(page = pageIndex, 20)

        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents.content,
                    prevKey = if (pageIndex == 0) null else pageIndex - 1,
                    nextKey = if (contents.isNotEmpty) pageIndex + 1 else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }

}
