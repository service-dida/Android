package com.dida.common.ui.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.bindingadapters.setOnSingleClickListener
import com.dida.common.databinding.HolderReportCodeBinding

internal class ReportCodeAdapter(
    private val eventListener: ReportActions,
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<ReportCode, ReviewReportCodeViewHolder>(ReportCodeDiffCallback) {

    init { setHasStableIds(true) }

    private var checkedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewReportCodeViewHolder {
        return ReviewReportCodeViewHolder(
            HolderReportCodeBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                eventListener = this@ReportCodeAdapter.eventListener
                lifecycleOwner = this@ReportCodeAdapter.lifecycleOwner
            }
        )
    }

    override fun onBindViewHolder(holder: ReviewReportCodeViewHolder, position: Int) {
        val checked = checkedPosition == position
        getItem(position)?.let {
            holder.bind(it, checked) { checkPos ->
                checkPos?.let { pos ->
                    checkedPosition = pos
                    currentList.getOrNull(checkedPosition)?.let { code -> eventListener.onReportCodeSelected(code) }
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1
}

internal class ReviewReportCodeViewHolder(
    private val binding: HolderReportCodeBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(code: ReportCode, checked: Boolean, callback: (Int?) -> Unit) {
        binding.code = code
        binding.isChecked = checked
        if (!checked) binding.reportContents.clearFocus()
        binding.executePendingBindings()

        binding.reportRadioButton.setOnSingleClickListener {
            binding.reportRadioButton.isChecked = true
            binding.reportContents.requestFocus()
            callback(bindingAdapterPosition)
        }

        binding.reportTitle.setOnSingleClickListener {
            binding.reportRadioButton.isChecked = true
            binding.reportContents.requestFocus()
            callback(bindingAdapterPosition)
        }

        binding.reportContents.setOnClickListener {
            binding.reportRadioButton.isChecked = true
            binding.reportContents.requestFocus()
            callback(bindingAdapterPosition)
        }
    }
}

internal object ReportCodeDiffCallback : DiffUtil.ItemCallback<ReportCode>() {
    override fun areItemsTheSame(oldItem: ReportCode, newItem: ReportCode) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: ReportCode, newItem: ReportCode) =
        oldItem == newItem
}
