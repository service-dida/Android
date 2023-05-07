package com.dida.common.ui.report

import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.ReportCardAPI
import com.dida.domain.usecase.main.ReportPostAPI
import com.dida.domain.usecase.main.ReportUserAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ReportViewModelDelegate {

    val navigateToReportSuccessEvent: MutableSharedFlow<Unit>
    val navigateToBlockSuccessEvent: MutableSharedFlow<Unit>
    val navigateToErrorEvent: MutableSharedFlow<String>

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
): ReportViewModelDelegate {

    private val _navigateToReportEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    private val _navigateToBlockEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    private val _navigateToErrorEvent: MutableSharedFlow<String> = MutableSharedFlow()

    override val navigateToBlockSuccessEvent: MutableSharedFlow<Unit>
        get() = _navigateToBlockEvent

    override val navigateToReportSuccessEvent: MutableSharedFlow<Unit>
        get() = _navigateToReportEvent

    override val navigateToErrorEvent: MutableSharedFlow<String>
        get() = _navigateToErrorEvent

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
            }.onSuccess { _navigateToReportEvent.emit(Unit)
            }.onError { _navigateToErrorEvent.emit(it.message ?: "알 수 없는 에러가 발생 했어요") }
        }
    }

    // TODO : 차단 API 추가 연결하기
    override fun onBlockDelegate(coroutineScope: CoroutineScope, type: ReportType, blockId: Long) {
    }

}
