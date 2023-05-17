package com.dida.common.ui.report

import com.dida.common.base.BaseViewModel
import com.dida.data.model.AlreadyReport
import com.dida.data.model.HaveNotJwtTokenException
import com.dida.data.model.InvalidKakaoAccessTokenException
import com.dida.data.model.NeedLogin
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.ReportCardAPI
import com.dida.domain.usecase.main.ReportPostAPI
import com.dida.domain.usecase.main.ReportUserAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ReportViewModelDelegate {

    val navigateToReportEvent: SharedFlow<Boolean>
    val navigateToBlockEvent: SharedFlow<Boolean>

    fun onReportDelegate(
        coroutineScope: CoroutineScope,
        type: ReportType, reportId: Long,
        content: String
    )

    fun onBlockDelegate(
        coroutineScope: CoroutineScope,
        type: ReportType,
        blockId: Long
     )
}

class DefaultReportViewModelDelegate @Inject constructor(
    private val reportUserAPI: ReportUserAPI,
    private val reportPostAPI: ReportPostAPI,
    private val reportCardAPI: ReportCardAPI
): ReportViewModelDelegate, BaseViewModel() {

    private val _navigateToReportEvent: MutableSharedFlow<Boolean> = MutableSharedFlow()
    private val _navigateToBlockEvent: MutableSharedFlow<Boolean> = MutableSharedFlow()

    override val navigateToReportEvent: SharedFlow<Boolean>
        get() = _navigateToReportEvent.asSharedFlow()

    override val navigateToBlockEvent: SharedFlow<Boolean>
        get() = _navigateToBlockEvent.asSharedFlow()

    override fun onReportDelegate(
        coroutineScope: CoroutineScope,
        type: ReportType,
        reportId: Long,
        content: String
    ) {
        coroutineScope.launch {
            when (type) {
                ReportType.USER -> reportUserAPI(userId = reportId, content = content)
                ReportType.POST -> reportPostAPI(postId = reportId, content = content)
                ReportType.CARD -> reportCardAPI(cardId = reportId, content = content)
            }.onSuccess { _navigateToReportEvent.emit(true)
            }.onError {
                when(it) {
                    is AlreadyReport -> _navigateToReportEvent.emit(false)
                    else -> catchError(it)
                }
            }
        }
    }

    // TODO : 차단 API 추가 연결하기
    override fun onBlockDelegate(coroutineScope: CoroutineScope, type: ReportType, blockId: Long) {
    }

}
