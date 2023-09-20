package com.dida.common.ui.report

import com.dida.common.base.BaseViewModel
import com.dida.domain.main.model.Block
import com.dida.domain.main.model.Report
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.BlockUseCase
import com.dida.domain.usecase.ReportUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

interface ReportViewModelDelegate {

    val navigateToReportEvent: SharedFlow<Pair<Report, Boolean>>
    val navigateToBlockEvent: SharedFlow<Pair<Block, Boolean>>

    suspend fun onReportDelegate(
        type: Report,
        reportId: Long,
        content: String
    )

    suspend fun onBlockDelegate(
        type: Block,
        blockId: Long
     )
}

class DefaultReportViewModelDelegate @Inject constructor(
    private val reportUseCase: ReportUseCase,
    private val blockUseCase: BlockUseCase,
): ReportViewModelDelegate, BaseViewModel() {

    private val _navigateToReportEvent: MutableSharedFlow<Pair<Report, Boolean>> = MutableSharedFlow()
    override val navigateToReportEvent: SharedFlow<Pair<Report, Boolean>> = _navigateToReportEvent.asSharedFlow()

    private val _navigateToBlockEvent: MutableSharedFlow<Pair<Block, Boolean>> = MutableSharedFlow()
    override val navigateToBlockEvent: SharedFlow<Pair<Block, Boolean>> = _navigateToBlockEvent.asSharedFlow()

    override suspend fun onReportDelegate(
        type: Report,
        reportId: Long,
        content: String
    ) {
        reportUseCase(type = type, reportedId = reportId, description = content)
            .onSuccess { _navigateToReportEvent.emit(Pair(type, true)) }
            .onError { e -> catchError(e) }
    }

    override suspend fun onBlockDelegate(type: Block, blockId: Long) {
        blockUseCase(type = type, blockId = blockId)
            .onSuccess { _navigateToBlockEvent.emit(Pair(type, true)) }
            .onError { _navigateToBlockEvent.emit(Pair(type, false)) }
    }

}
