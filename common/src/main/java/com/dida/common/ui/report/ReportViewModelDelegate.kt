package com.dida.common.ui.report

import com.dida.common.base.BaseViewModel
import com.dida.domain.main.model.Block
import com.dida.domain.main.model.Report
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.BlockUseCase
import com.dida.domain.usecase.ReportUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ReportViewModelDelegate {

    val navigateToReportEvent: SharedFlow<Pair<ReportType, Boolean>>
    val navigateToBlockEvent: SharedFlow<Pair<ReportType, Boolean>>

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
    private val reportUseCase: ReportUseCase,
    private val blockUseCase: BlockUseCase
): ReportViewModelDelegate, BaseViewModel() {

    private val _navigateToReportEvent: MutableSharedFlow<Pair<ReportType, Boolean>> = MutableSharedFlow()
    override val navigateToReportEvent: SharedFlow<Pair<ReportType, Boolean>> = _navigateToReportEvent.asSharedFlow()

    private val _navigateToBlockEvent: MutableSharedFlow<Pair<ReportType, Boolean>> = MutableSharedFlow()
    override val navigateToBlockEvent: SharedFlow<Pair<ReportType, Boolean>> = _navigateToBlockEvent.asSharedFlow()

    // TODO : 서버 API 에러 관련 수정 필요
    override fun onReportDelegate(
        coroutineScope: CoroutineScope,
        type: ReportType,
        reportId: Long,
        content: String
    ) {
        coroutineScope.launch {
            when (type) {
                ReportType.USER -> reportUseCase(type = Report.MEMBER, reportedId = reportId, description = content)
                ReportType.POST -> reportUseCase(type = Report.POST, reportedId = reportId, description = content)
                ReportType.CARD -> reportUseCase(type = Report.NFT, reportedId = reportId, description = content)
            }.onSuccess { _navigateToReportEvent.emit(Pair(type, true))
            }.onError {
                when(it) {
//                    is AlreadyReport -> _navigateToReportEvent.emit(Pair(type, false))
                    else -> catchError(it)
                }
            }
        }
    }

    // TODO : 차단 API 추가 연결하기
    override fun onBlockDelegate(coroutineScope: CoroutineScope, type: ReportType, blockId: Long) {
        coroutineScope.launch {
            when (type) {
                ReportType.POST -> blockUseCase(type = Block.POST, blockId = blockId)
                else -> blockUseCase(type = Block.MEMBER, blockId = blockId)
            }.onSuccess { _navigateToBlockEvent.emit(Pair(type, true))
            }.onError { _navigateToBlockEvent.emit(Pair(type, false)) }
        }
    }

}
