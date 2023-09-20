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

    val navigateToReportEvent: SharedFlow<Pair<Report, Boolean>>
    val navigateToBlockEvent: SharedFlow<Pair<Block, Boolean>>

    fun onReportDelegate(
        coroutineScope: CoroutineScope,
        type: Report,
        reportId: Long,
        content: String
    )

    fun onBlockDelegate(
        coroutineScope: CoroutineScope,
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

    override fun onReportDelegate(
        coroutineScope: CoroutineScope,
        type: Report,
        reportId: Long,
        content: String
    ) {
        coroutineScope.launch {
            reportUseCase(type = type, reportedId = reportId, description = content)
                .onSuccess { _navigateToReportEvent.emit(Pair(type, true)) }
                .onError { e -> catchError(e) }
        }
    }

    override fun onBlockDelegate(coroutineScope: CoroutineScope, type: Block, blockId: Long) {
        coroutineScope.launch {
            blockUseCase(type = type, blockId = blockId)
                .onSuccess { _navigateToBlockEvent.emit(Pair(type, true)) }
                .onError { _navigateToBlockEvent.emit(Pair(type, false)) }
        }
    }

}
