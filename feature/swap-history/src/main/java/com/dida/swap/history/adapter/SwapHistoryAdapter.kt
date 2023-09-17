package com.dida.swap.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.main.model.Swap
import com.dida.swap.history.R
import com.dida.swap.history.SwapHistoryActionHandler
import com.dida.swap.history.databinding.HolderSwapHistoryBinding

class SwapHistoryAdapter(
    private val eventListener : SwapHistoryActionHandler
): ListAdapter<Swap, SwapHistoryAdapter.ViewHolder>(SwapHistoryDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderSwapHistoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_swap_history,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = R.layout.holder_swap_history

    class ViewHolder(private val binding: HolderSwapHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Swap) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object SwapHistoryDiffCallback : DiffUtil.ItemCallback<Swap>() {
        override fun areItemsTheSame(oldItem: Swap, newItem: Swap) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Swap, newItem: Swap) =
            oldItem == newItem
    }
}
