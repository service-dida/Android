package com.dida.hot_seller.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dida.domain.fold
import com.dida.domain.model.main.HotSellerMore
import com.dida.domain.usecase.main.HotSellerAPI

fun createHotSellerPager(
    hotSellerAPI: HotSellerAPI
): Pager<Int, HotSellerMore> = Pager(
    config = PagingConfig(pageSize = 20, initialLoadSize = 20, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = { HotSellerPagingSource(hotSellerAPI = hotSellerAPI) }
)

class HotSellerPagingSource(
    private val hotSellerAPI: HotSellerAPI
) : PagingSource<Int, HotSellerMore>() {

    override fun getRefreshKey(state: PagingState<Int, HotSellerMore>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HotSellerMore> {
        val pageIndex = params.key ?: 0
        val result = hotSellerAPI(page = pageIndex)
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
