package com.dida.create_community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.create_community.R
import com.dida.create_community.databinding.HolderCreateCommunityNftBinding
import com.dida.domain.main.model.OwnNft

class CreateCommunityNftAdapter(
    private val eventListener: com.dida.create_community.CreateCommunityActionHandler
) : ListAdapter<OwnNft, CreateCommunityNftAdapter.ViewHolder>(
    CreateCommunityNftItemDiffCallback
) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderCreateCommunityNftBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_create_community_nft,
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

    override fun getItemViewType(position: Int): Int = R.layout.holder_create_community_nft

    class ViewHolder(private val binding: HolderCreateCommunityNftBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OwnNft) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object CreateCommunityNftItemDiffCallback : DiffUtil.ItemCallback<OwnNft>() {
        override fun areItemsTheSame(oldItem: OwnNft, newItem: OwnNft) =
            oldItem.nftId == newItem.nftId

        override fun areContentsTheSame(oldItem: OwnNft, newItem: OwnNft) =
            oldItem == newItem
    }
}
