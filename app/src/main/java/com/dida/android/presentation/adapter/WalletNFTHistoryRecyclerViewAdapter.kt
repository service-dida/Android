package com.dida.android.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.databinding.HolderWalletNftHistoryBinding
import com.dida.android.domain.model.nav.mypage.WalletNFTHistoryHolderModel

class WalletNFTHistoryRecyclerViewAdapter(private val modelList: List<WalletNFTHistoryHolderModel>): RecyclerView.Adapter<WalletNFTHistoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = HolderWalletNftHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderModel = modelList[position]
        holder.bind(holderModel)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    inner class ViewHolder(val viewDataBinding: HolderWalletNftHistoryBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(holderModel: WalletNFTHistoryHolderModel) {
            viewDataBinding.holderModel = holderModel

        }
    }
}