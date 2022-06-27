package com.dida.android.presentation.adapter.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.databinding.HolderWalletCardBinding
import com.dida.android.domain.model.nav.mypage.WalletCardHolderModel

class WalletCardRecyclerViewAdapter(private val modelList: List<WalletCardHolderModel>): RecyclerView.Adapter<WalletCardRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = HolderWalletCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderModel = modelList[position]
        holder.bind(holderModel)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    inner class ViewHolder(val viewDataBinding: HolderWalletCardBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(holderModel: WalletCardHolderModel) {
            viewDataBinding.holderModel = holderModel

        }
    }
}