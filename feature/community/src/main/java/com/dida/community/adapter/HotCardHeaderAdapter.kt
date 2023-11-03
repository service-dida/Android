package com.dida.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.community.databinding.HolderHotCardHeaderBinding

class HotCardHeaderAdapter: ListAdapter<HotCardHeaderItem, HotCardHeaderAdapter.ViewHolder>(HotCardHeaderDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderHotCardHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind() }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    class ViewHolder(private val binding: HolderHotCardHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.executePendingBindings()
        }
    }

    internal object HotCardHeaderDiffCallback : DiffUtil.ItemCallback<HotCardHeaderItem>() {
        override fun areItemsTheSame(oldItem: HotCardHeaderItem, newItem: HotCardHeaderItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HotCardHeaderItem, newItem: HotCardHeaderItem) =
            oldItem.equals(newItem)
    }
}

object HotCardHeaderItem




