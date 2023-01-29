package com.dida.create_community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.create_community.R
import com.dida.create_community.databinding.HolderCreateCommunityNftBinding
import com.dida.domain.model.nav.community.CreateCommunityNft

class CreateCommunityNftAdapter(
    private val eventListener: com.dida.create_community.CreateCommunityActionHandler
) : ListAdapter<CreateCommunityNft, CreateCommunityNftAdapter.ViewHolder>(
    CreateCommunityNftItemDiffCallback
){

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

    class ViewHolder(private val binding: HolderCreateCommunityNftBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CreateCommunityNft) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object CreateCommunityNftItemDiffCallback : DiffUtil.ItemCallback<CreateCommunityNft>() {
        override fun areItemsTheSame(oldItem: CreateCommunityNft, newItem: CreateCommunityNft) =
            oldItem.nftId == newItem.nftId

        override fun areContentsTheSame(oldItem: CreateCommunityNft, newItem: CreateCommunityNft) =
            oldItem == newItem
    }
}