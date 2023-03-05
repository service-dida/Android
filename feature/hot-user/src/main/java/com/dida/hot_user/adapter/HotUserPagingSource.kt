package com.dida.hot_user.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dida.domain.fold
import com.dida.domain.model.nav.home.HotUser
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.post.Posts
import com.dida.domain.usecase.main.HotUserAPI
import com.dida.domain.usecase.main.PostsAPI
import com.dida.domain.usecase.main.RecentCardAPI

fun createHotUserPager(
    hotUserAPI: HotUserAPI
): Pager<Int, HotUser> = Pager(
    config = PagingConfig(pageSize = 20, initialLoadSize = 20, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = { HotUserPagingSource(hotUserAPI = hotUserAPI) }
)

class HotUserPagingSource(
    private val hotUserAPI: HotUserAPI
) : PagingSource<Int, HotUser>() {

    override fun getRefreshKey(state: PagingState<Int, HotUser>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HotUser> {
        val pageIndex = params.key ?: 0
        val result = hotUserAPI.invoke(
            page = pageIndex
        )
        return result.fold(
            onSuccess = { contents ->
                LoadResult.Page(
                    data = contents,
                    prevKey = null,
                    nextKey = if(contents.isNotEmpty()) pageIndex+1 else null
                )
            },
            onError = { e -> LoadResult.Error(e) }
        )
    }

}
