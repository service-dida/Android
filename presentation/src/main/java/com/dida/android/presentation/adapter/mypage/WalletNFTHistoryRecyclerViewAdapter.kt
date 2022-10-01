package com.dida.android.presentation.adapter.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.databinding.HolderWalletNftHistoryBinding
import com.dida.domain.model.nav.mypage.WalletNFTHistoryHolderModel

class WalletNFTHistoryRecyclerViewAdapter(
) : ListAdapter<WalletNFTHistoryHolderModel, WalletNFTHistoryRecyclerViewAdapter.ViewHolder>(WalletCardDiffCallback){

    init { setHasStableIds(true) }

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

    class ViewHolder(private val binding: HolderWalletNftHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WalletNFTHistoryHolderModel) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object WalletCardDiffCallback : DiffUtil.ItemCallback<WalletNFTHistoryHolderModel>() {
        override fun areItemsTheSame(oldItem: WalletNFTHistoryHolderModel, newItem: WalletNFTHistoryHolderModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: WalletNFTHistoryHolderModel, newItem: WalletNFTHistoryHolderModel) =
            oldItem.equals(newItem)
    }
}