package com.dida.notification

import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGE_SIZE
import com.dida.domain.Contents
import com.dida.domain.main.model.Alarm
import com.dida.domain.main.model.AlarmType
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.AlarmsUseCase
import com.dida.domain.usecase.ReadAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val alarmsUseCase: AlarmsUseCase,
    private val readAlarmUseCase: ReadAlarmUseCase
) : BaseViewModel() {

    private val TAG = "NotificationViewModel"

    private val _navigationAction: MutableSharedFlow<NotificationNavigationAction> = MutableSharedFlow<NotificationNavigationAction>()
    val navigationAction: SharedFlow<NotificationNavigationAction> = _navigationAction

    private val _notifications: MutableStateFlow<Contents<Alarm>> = MutableStateFlow(
        Contents(page = 0, pageSize = 0, hasNext = true, content = emptyList())
    )
    val notifications: StateFlow<Contents<Alarm>> = _notifications.asStateFlow()

    init {
        baseViewModelScope.launch {
            alarmsUseCase(page = 0, size = PAGE_SIZE)
                .onSuccess { _notifications.value = it }
                .onError { e -> catchError(e) }
        }
    }

    fun onNotificationClicked(alarm: Alarm) {
        baseViewModelScope.launch {
            onReadNotification(alarmId = alarm.alarmId).join()
            when (alarm.type) {
                AlarmType.SALE, AlarmType.LIKE -> _navigationAction.emit(NotificationNavigationAction.NavigateToNft(alarm.id))
                AlarmType.COMMENT -> _navigationAction.emit(NotificationNavigationAction.NavigateToPost(alarm.id))
                AlarmType.FOLLOW -> _navigationAction.emit(NotificationNavigationAction.NavigateToUserFollowed)
            }
        }
    }

    private fun onReadNotification(alarmId: Long) = baseViewModelScope.launch {
        showLoading()
        readAlarmUseCase(alarmId = alarmId)
            .onSuccess {
                val newList = notifications.value.content.toMutableList()
                val alarmIndex = notifications.value.content.indexOfFirst { it.alarmId == alarmId }
                val beforeAlarm = newList[alarmIndex]
                val newNft = Alarm(
                    alarmId = beforeAlarm.alarmId,
                    type = beforeAlarm.type,
                    id = beforeAlarm.id,
                    watched = true,
                    date = beforeAlarm.date
                )
                newList[alarmIndex] = newNft
                _notifications.value = Contents(
                    page = notifications.value.page,
                    pageSize = notifications.value.pageSize,
                    hasNext = notifications.value.hasNext,
                    content = newList
                )
            }.onError { e -> catchError(e) }
        dismissLoading()
    }

    fun nextPage() {
        baseViewModelScope.launch {
            if (!notifications.value.hasNext) return@launch
            showLoading()
            alarmsUseCase(notifications.value.page + 1, PAGE_SIZE)
                .onSuccess {
                    it.content = (notifications.value.content.toMutableList()) + it.content
                    _notifications.value = it
                }.onError { e -> catchError(e) }
            dismissLoading()
        }
    }
}
