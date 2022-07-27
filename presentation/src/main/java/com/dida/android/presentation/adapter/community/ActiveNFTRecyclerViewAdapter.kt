package com.dida.android.presentation.adapter.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.databinding.HolderActiveNftBinding
import com.dida.domain.model.nav.community.ActiveNFTHolderModel

class ActiveNFTRecyclerViewAdapter(private val modelList: List<ActiveNFTHolderModel>): RecyclerView.Adapter<ActiveNFTRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = HolderActiveNftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderModel = modelList[position]
        holder.bind(holderModel)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    inner class ViewHolder(val viewDataBinding: HolderActiveNftBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(holderModel: ActiveNFTHolderModel) {
            viewDataBinding.holderModel = holderModel

        }
    }
}