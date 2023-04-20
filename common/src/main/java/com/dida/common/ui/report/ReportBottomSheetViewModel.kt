package com.dida.common.ui.report

import com.dida.common.base.BaseViewModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.main.ReportPostAPI
import com.dida.domain.usecase.main.ReportUserAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportBottomSheetViewModel @Inject constructor(
    val reportUserAPI: ReportUserAPI,
    val reportPostAPI: ReportPostAPI
) : BaseViewModel(), ReportActions {
    private val TAG = "ImageBottomSheetViewModel"

    private val _reportCodes: MutableStateFlow<List<ReportCode>> = MutableStateFlow<List<ReportCode>>(emptyList())
    val reportCodes: StateFlow<List<ReportCode>> = _reportCodes.asStateFlow()

    private var _selectedReportCode: MutableStateFlow<ReportCode>? = null

    private val _hasSelectedReportCode: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val hasSelectedReportCode: StateFlow<Boolean> = _hasSelectedReportCode

    private val _reportSuccess: MutableSharedFlow<Unit> = MutableSharedFlow()
    val reportSuccess: SharedFlow<Unit> = _reportSuccess.asSharedFlow()

    private val _isUserReport: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _reportId: MutableStateFlow<Long> = MutableStateFlow(0)

    fun setReports(reportId: Long, isUserReport: Boolean) {
        _reportId.value = reportId
        _isUserReport.value = isUserReport
    }

    fun setReportCode(codes: List<ReportCode>) {
        _reportCodes.value = codes
    }

    // TODO : 신고하기 API 추가
    override fun onReportConfirmButtonClicked() {
        baseViewModelScope.launch {
            val result =
                if (_isUserReport.value) { reportUserAPI(userId = _reportId.value, content = "") }
                else { reportPostAPI(postId = _reportId.value, content = "") }
            result
                .onSuccess { _reportSuccess.emit(Unit) }
                .onError { e -> catchError(e) }
        }
    }

    override fun onReportCodeSelected(code: ReportCode) {
        _selectedReportCode?.value = code
        _hasSelectedReportCode.value = true
    }
}
