package com.dida.wallet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.main.model.DealingHistory
import com.dida.wallet.R
import com.dida.wallet.databinding.HolderWalletNftHistoryBinding

class WalletHistoryAdapter(
) : ListAdapter<DealingHistory, WalletHistoryAdapter.ViewHolder>(WalletNftHistoryDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderWalletNftHistoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_wallet_nft_history,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = R.layout.holder_wallet_nft_history

    class ViewHolder(private val binding: HolderWalletNftHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DealingHistory) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object WalletNftHistoryDiffCallback : DiffUtil.ItemCallback<DealingHistory>() {
        override fun areItemsTheSame(oldItem: DealingHistory, newItem: DealingHistory) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: DealingHistory, newItem: DealingHistory) =
            oldItem == newItem
    }

}
