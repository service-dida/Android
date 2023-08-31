package com.dida.notification.list.adapter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dida.domain.fold
import com.dida.domain.model.main.CommonAlarm
import com.dida.domain.model.main.Posts
import com.dida.domain.usecase.main.NotificationListAPI
import com.dida.domain.usecase.main.PostsAPI

fun createNotificationPager(
    notificationListAPI: NotificationListAPI
): Pager<Int, CommonAlarm> = Pager(
    config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true),
    initialKey = 0,
    pagingSourceFactory = { NotificationsPagingSource(notificationListAPI = notificationListAPI) }
)

class NotificationsPagingSource(
    private val notificationListAPI: NotificationListAPI
) : PagingSource<Int, CommonAlarm>() {

    override fun getRefreshKey(state: PagingState<Int, CommonAlarm>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommonAlarm> {
        val pageIndex = params.key ?: 0
        val result = notificationListAPI.invoke(page = pageIndex,10)

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
