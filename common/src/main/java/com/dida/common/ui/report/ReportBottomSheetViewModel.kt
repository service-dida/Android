package com.dida.common.ui.report

import com.dida.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportBottomSheetViewModel @Inject constructor(
) : BaseViewModel(), ReportActions {
    private val TAG = "ImageBottomSheetViewModel"

    private val _reportCodes: MutableStateFlow<List<ReportCode>> = MutableStateFlow<List<ReportCode>>(emptyList())
    val reportCodes: StateFlow<List<ReportCode>> = _reportCodes.asStateFlow()

    private var _selectedReportCode: MutableStateFlow<ReportCode>? = null

    private val _hasSelectedReportCode: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val hasSelectedReportCode: StateFlow<Boolean> = _hasSelectedReportCode

    private val _reportSuccess: MutableSharedFlow<Unit> = MutableSharedFlow()
    val reportSuccess: SharedFlow<Unit> = _reportSuccess.asSharedFlow()

    private val _userId: MutableStateFlow<Long> = MutableStateFlow(0)

    fun setUserId(userId: Long) {
        _userId.value = userId
    }

    fun setReportCode(codes: List<ReportCode>) {
        _reportCodes.value = codes
    }

    // TODO : 신고하기 API 추가
    override fun onReportConfirmButtonClicked() {
        baseViewModelScope.launch {
            _reportSuccess.emit(Unit)
        }
    }

    override fun onReportCodeSelected(code: ReportCode) {
        _selectedReportCode?.value = code
        _hasSelectedReportCode.value = true
    }
}
