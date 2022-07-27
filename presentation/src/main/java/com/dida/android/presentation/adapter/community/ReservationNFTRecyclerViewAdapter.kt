package com.dida.android.presentation.adapter.community

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.databinding.HolderReservationNftBinding
import com.dida.domain.model.nav.community.ReservationNFTHolderModel

class ReservationNFTRecyclerViewAdapter(private val modelList: List<ReservationNFTHolderModel>): RecyclerView.Adapter<ReservationNFTRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = HolderReservationNftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderModel = modelList[position]
        holder.bind(holderModel)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    inner class ViewHolder(val viewDataBinding: HolderReservationNftBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(holderModel: ReservationNFTHolderModel) {
            viewDataBinding.holderModel = holderModel

            //이미지 어둡게 처리
            viewDataBinding.nftImageView.setColorFilter( Color.parseColor("#757575"), PorterDuff.Mode.MULTIPLY )

        }
    }
}