package com.dida.common.ui.report

import androidx.fragment.app.viewModels
import com.dida.common.R
import com.dida.common.base.BaseBottomSheetDialogFragment
import com.dida.common.databinding.DialogBottomImageBinding
import com.dida.common.databinding.DialogBottomReportBinding
import com.dida.common.util.repeatOnResumed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReportBottomSheet(
    val userId: Long,
    val callback: (isReport: Boolean) -> Unit
) : BaseBottomSheetDialogFragment<DialogBottomReportBinding, ReportBottomSheetViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_bottom_report

    override val viewModel: ReportBottomSheetViewModel by viewModels()

    private val reportCodeAdapter: ReportCodeAdapter by lazy {
        ReportCodeAdapter(viewModel, viewLifecycleOwner)
    }
    override fun initStartView() {
        binding.apply {
            this.vm
            this.lifecycleOwner = viewLifecycleOwner
        }
        val reportCodes = listOf<ReportCode>(
            ReportCode.SPAM,
            ReportCode.ADVISE,
            ReportCode.SEXUAL,
            ReportCode.PRIVACY,
            ReportCode.OTHER
        )
        viewModel.setUserId(userId)
        viewModel.setReportCode(reportCodes)
    }

    override fun initDataBinding() {
        repeatOnResumed {
            launch {
                viewModel.reportCodes.collectLatest {
                    reportCodeAdapter.submitList(it)
                }
            }

            launch {
                viewModel.reportSuccess.collectLatest {
                    dismissAllowingStateLoss()
                }
            }

            launch {
                viewModel.hasSelectedReportCode.collectLatest {
                    binding.reportConfirmButton.setReportEnabled(it)
                }
            }
        }

    }

    override fun initAfterBinding() {
        binding.reportRecyclerView.adapter = reportCodeAdapter
        binding.closeBtn.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }
}
