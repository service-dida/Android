package com.dida.common.ui.report

 import com.dida.common.base.BaseViewModel
 import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportBottomSheetViewModel @Inject constructor() : BaseViewModel(), ReportActions {
    private val TAG = "ImageBottomSheetViewModel"

    private val _reportCodes: MutableStateFlow<List<ReportCode>> =
        MutableStateFlow(
            listOf(
                ReportCode.SPAM,
                ReportCode.ADVISE,
                ReportCode.SEXUAL,
                ReportCode.PRIVACY,
                ReportCode.OTHER
            )
        )
    val reportCodes: StateFlow<List<ReportCode>> = _reportCodes.asStateFlow()

    private var _selectedReportCode: MutableStateFlow<ReportCode> = MutableStateFlow(ReportCode.SPAM)
    val selectedReportCode: StateFlow<ReportCode> = _selectedReportCode.asStateFlow()

    private val _hasSelectedReportCode: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val hasSelectedReportCode: StateFlow<Boolean> = _hasSelectedReportCode

    val contents: MutableStateFlow<String> = MutableStateFlow("")

    fun setEnableConfirm(enable: Boolean) {
        _hasSelectedReportCode.value = enable
    }

    override fun onReportCodeSelected(code: ReportCode) {
        _selectedReportCode.value = code
        _hasSelectedReportCode.value = true
        contents.value = code.message
    }
}
