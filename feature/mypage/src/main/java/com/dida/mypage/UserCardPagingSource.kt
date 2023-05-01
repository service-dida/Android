package com.dida.mypage

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dida.domain.NetworkResult
import com.dida.domain.fold
import com.dida.domain.model.main.Posts
import com.dida.domain.model.main.UserNft
import com.dida.domain.usecase.main.PostsAPI
import com.dida.domain.usecase.main.UserNftAPI

fun createUserCardPager(
    userNftAPI: UserNftAPI
): Pager<Int, UserNft> = Pager(
    config = PagingConfig(pageSize = 20, initialLoadSize = 20, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = { UserCardPagingSource(userNftAPI = userNftAPI) }
)

class UserCardPagingSource(
    private val userNftAPI: UserNftAPI
) : PagingSource<Int, UserNft>() {

    override fun getRefreshKey(state: PagingState<Int, UserNft>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserNft> {
        val pageIndex = params.key ?: 0
        val result = userNftAPI(page = pageIndex)

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
