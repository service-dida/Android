package com.dida.android.presentation.adapter.community

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.databinding.HolderHotsBinding
import com.dida.android.databinding.HolderReservationNftBinding
import com.dida.android.presentation.adapter.home.HotsAdapter
import com.dida.domain.model.nav.community.ReservationNFTHolderModel
import com.dida.domain.model.nav.home.Hots

class ReservationNFTRecyclerViewAdapter(
) : ListAdapter<ReservationNFTHolderModel, ReservationNFTRecyclerViewAdapter.ViewHolder>(ReservationNFTDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderReservationNftBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_reservation_nft,
            parent,
            false
        )
//        viewDataBinding.root.setOnClickListener {
//            onClick.invoke(viewDataBinding.holderModel!!.cardId)
//        }
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
            oldItem.equals(newItem)
    }
}