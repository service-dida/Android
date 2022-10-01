package com.dida.android.presentation.adapter.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.databinding.HolderActiveNftBinding
import com.dida.domain.model.nav.community.ActiveNFTHolderModel

class ActiveNFTRecyclerViewAdapter(
) : ListAdapter<ActiveNFTHolderModel, ActiveNFTRecyclerViewAdapter.ViewHolder>(ActivieNftDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderActiveNftBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_active_nft,
            parent,
            false
        )
//        viewDataBinding.root.setOnClickListener {
//            eventListener.onCommunityItemClicked(viewDataBinding.holderModel!!.)
//        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderActiveNftBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ActiveNFTHolderModel) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object ActivieNftDiffCallback : DiffUtil.ItemCallback<ActiveNFTHolderModel>() {
        override fun areItemsTheSame(oldItem: ActiveNFTHolderModel, newItem: ActiveNFTHolderModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ActiveNFTHolderModel, newItem: ActiveNFTHolderModel) =
            oldItem == newItem
    }
}