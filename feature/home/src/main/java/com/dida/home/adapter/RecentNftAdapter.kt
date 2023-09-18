package com.dida.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.R
import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.databinding.HolderMypageUserCardsBinding
import com.dida.domain.main.model.RecentNft
import com.dida.home.databinding.HolderRecentNftBinding

class RecentNftAdapter(
    private val eventListener: NftActionHandler
) : ListAdapter<RecentNft, RecentNftAdapter.ViewHolder>(RecentNftItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderRecentNftBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                com.dida.home.R.layout.holder_recent_nft,
                parent,
                false
            )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long = getItem(position).nftId * -1

    override fun getItemViewType(position: Int): Int = com.dida.home.R.layout.holder_recent_nft

    class ViewHolder(private val binding: HolderRecentNftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentNft) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object RecentNftItemDiffCallback : DiffUtil.ItemCallback<RecentNft>() {
        override fun areItemsTheSame(oldItem: RecentNft, newItem: RecentNft) =
            oldItem.nftId == newItem.nftId && oldItem.liked == newItem.liked

        override fun areContentsTheSame(oldItem: RecentNft, newItem: RecentNft) =
            oldItem == newItem
    }
}
