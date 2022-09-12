package com.dida.android.presentation.adapter.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.databinding.HolderCommentsBinding
import com.dida.android.databinding.HolderWalletCardBinding
import com.dida.android.presentation.adapter.detailnft.CommentsAdapter
import com.dida.domain.model.nav.detailnft.Comments
import com.dida.domain.model.nav.mypage.WalletCardHolderModel

class WalletCardRecyclerViewAdapter(
) : ListAdapter<WalletCardHolderModel, WalletCardRecyclerViewAdapter.ViewHolder>(WalletCardDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderWalletCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_wallet_card,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderWalletCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WalletCardHolderModel) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object WalletCardDiffCallback : DiffUtil.ItemCallback<WalletCardHolderModel>() {
        override fun areItemsTheSame(oldItem: WalletCardHolderModel, newItem: WalletCardHolderModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: WalletCardHolderModel, newItem: WalletCardHolderModel) =
            oldItem.equals(newItem)
    }
}