package com.dida.community.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.community.R
import com.dida.community.databinding.HolderReservationNftBinding
import com.dida.domain.model.nav.community.ReservationNFTHolderModel

class ReservationNFTRecyclerViewAdapter(
) : ListAdapter<ReservationNFTHolderModel, ReservationNFTRecyclerViewAdapter.ViewHolder>(ReservationNFTDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderReservationNftBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_reservation_nft,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderReservationNftBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReservationNFTHolderModel) {
            binding.holderModel = item
            binding.executePendingBindings()
            //이미지 어둡게 처리
            binding.nftImageView.setColorFilter( Color.parseColor("#757575"), PorterDuff.Mode.MULTIPLY )
        }
    }

    internal object ReservationNFTDiffCallback : DiffUtil.ItemCallback<ReservationNFTHolderModel>() {
        override fun areItemsTheSame(oldItem: ReservationNFTHolderModel, newItem: ReservationNFTHolderModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ReservationNFTHolderModel, newItem: ReservationNFTHolderModel) =
            oldItem == newItem
    }
}
