package com.dida.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.community.databinding.HolderCommunityHeaderBinding

class CommunityHeaderAdapter: ListAdapter<CommunityHeaderItem, CommunityHeaderAdapter.ViewHolder>(CommunityHeaderDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderCommunityHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind() }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    class ViewHolder(private val binding: HolderCommunityHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.executePendingBindings()
        }
    }

    internal object CommunityHeaderDiffCallback : DiffUtil.ItemCallback<CommunityHeaderItem>() {
        override fun areItemsTheSame(oldItem: CommunityHeaderItem, newItem: CommunityHeaderItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CommunityHeaderItem, newItem: CommunityHeaderItem) =
            oldItem.equals(newItem)
    }
}

object CommunityHeaderItem




