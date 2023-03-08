package com.dida.wallet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.model.main.Wallet
import com.dida.wallet.R
import com.dida.wallet.WalletActionHandler
import com.dida.wallet.databinding.HolderWalletCardBinding

class WalletAdapter(
    private val walletActionHandler: WalletActionHandler
) : ListAdapter<Wallet, WalletAdapter.ViewHolder>(
    WalletCardDiffCallback
){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderWalletCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_wallet_card,
            parent,
            false
        )
        viewDataBinding.eventListener = walletActionHandler
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = R.layout.holder_wallet_card

    class ViewHolder(private val binding: HolderWalletCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Wallet) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object WalletCardDiffCallback : DiffUtil.ItemCallback<Wallet>() {
        override fun areItemsTheSame(oldItem: Wallet, newItem: Wallet) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Wallet, newItem: Wallet) =
            oldItem.equals(newItem)
    }
}
