package com.dida.notification.list

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dida.common.actionhandler.NotificationActionHandler
import com.dida.common.base.BaseViewModel
import com.dida.domain.model.main.CommonAlarm
import com.dida.domain.usecase.main.NotificationListAPI
import com.dida.notification.list.adapter.createNotificationPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NotificationListViewModel @Inject constructor(
    private val notificatinoListAPI: NotificationListAPI
) : BaseViewModel(), NotificationActionHandler {

    private val TAG = "NotificationListViewModel"

    val notificationState: Flow<PagingData<CommonAlarm>> = createNotificationPager(notificatinoListAPI)
        .flow.cachedIn(baseViewModelScope)

}
