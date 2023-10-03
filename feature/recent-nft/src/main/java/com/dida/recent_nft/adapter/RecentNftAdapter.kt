package com.dida.recent_nft.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.actionhandler.NftActionHandler
import com.dida.domain.main.model.RecentNft
import com.dida.recent_nft.R
import com.dida.recent_nft.databinding.HolderRecentNftBinding

class RecentNftAdapter(
    private val eventListener: NftActionHandler
): ListAdapter<RecentNft, RecentNftAdapter.ViewHolder>(CardItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderRecentNftBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_recent_nft,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemId(position: Int): Long = getItem(position).nftId * -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_recent_nft

    class ViewHolder(private val binding: HolderRecentNftBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentNft) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object CardItemDiffCallback : DiffUtil.ItemCallback<RecentNft>() {
        override fun areItemsTheSame(oldItem: RecentNft, newItem: RecentNft) =
            oldItem.nftId == newItem.nftId && oldItem.liked == newItem.liked

        override fun areContentsTheSame(oldItem: RecentNft, newItem: RecentNft) =
            oldItem == newItem
    }
}
