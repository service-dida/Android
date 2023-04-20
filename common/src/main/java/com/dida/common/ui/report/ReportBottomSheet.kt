package com.dida.common.ui.report

import androidx.fragment.app.viewModels
import com.dida.common.R
import com.dida.common.base.BaseBottomSheetDialogFragment
import com.dida.common.databinding.DialogBottomReportBinding
import com.dida.common.util.repeatOnResumed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReportBottomSheet(
    val callback: (reportConfirm: Boolean, content: String) -> Unit
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
        binding.reportRecyclerView.adapter = reportCodeAdapter
    }

    override fun initDataBinding() {
        repeatOnResumed {
            launch {
                viewModel.reportCodes.collectLatest {
                    reportCodeAdapter.submitList(it)
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
        binding.closeBtn.setOnClickListener {
            callback.invoke(false, viewModel.contents.value)
            dismissAllowingStateLoss()
        }

        binding.reportConfirmButton.setOnClickListener {
            if (viewModel.hasSelectedReportCode.value) {
                callback.invoke(true, viewModel.contents.value)
                dismissAllowingStateLoss()
            }
        }
    }
}

enum class ReportType {
    USER, POST
}
