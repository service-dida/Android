package com.dida.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.community.R
import com.dida.community.databinding.HolderActiveNftBinding
import com.dida.domain.model.nav.community.HotCard

class ActiveNFTRecyclerViewAdapter(
) : ListAdapter<HotCard, ActiveNFTRecyclerViewAdapter.ViewHolder>(ActivieNftDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderActiveNftBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_active_nft,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderActiveNftBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HotCard) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object ActivieNftDiffCallback : DiffUtil.ItemCallback<HotCard>() {
        override fun areItemsTheSame(oldItem: HotCard, newItem: HotCard) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HotCard, newItem: HotCard) =
            oldItem == newItem
    }
}
