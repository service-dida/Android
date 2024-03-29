package com.dida.common.ui.report

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import com.dida.common.R
import com.dida.common.base.BaseBottomSheetDialogFragment
import com.dida.common.databinding.DialogBottomReportBinding
import com.dida.common.util.repeatOnResumed
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
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

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable?) {
            binding.reportContents.let {
                viewModel.setEnableConfirm(!it.text.isNullOrBlank())
                viewModel.contents.value = if (!it.text.isNullOrBlank()) it.text.toString() else ""
            }
        }
    }

    override fun initStartView() {
        binding.apply {
            this.vm
            this.lifecycleOwner = viewLifecycleOwner
        }
        binding.reportRecyclerView.adapter = reportCodeAdapter
    }

    override fun initDataBinding() {
        viewLifecycleOwner.repeatOnResumed {
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

            launch {
                viewModel.selectedReportCode.collectLatest {
                    when (it) {
                        ReportCode.OTHER -> setReportContentsEnable(true)
                        else -> setReportContentsEnable(false)
                    }
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

        binding.reportContents.addTextChangedListener(textWatcher)
    }

    private fun setReportContentsEnable(enable: Boolean) {
        binding.reportContents.apply {
            this.isEnabled = enable
            this.isClickable = enable
            if (!enable) this.text = null
            if (enable) this.requestFocus() else this.clearFocus()
        }

        if (enable) {
            Handler(Looper.getMainLooper()).post {
                binding.reportScrollView.smoothScrollTo(0, binding.reportPaddingView.bottom)
            }
        }
    }
}
