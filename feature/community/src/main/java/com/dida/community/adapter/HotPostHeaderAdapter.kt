package com.dida.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.community.databinding.HolderHotPostHeaderBinding

class HotPostHeaderAdapter: ListAdapter<HotPostHeaderItem, HotPostHeaderAdapter.ViewHolder>(HotPostHeaderDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderHotPostHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind() }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    class ViewHolder(private val binding: HolderHotPostHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.executePendingBindings()
        }
    }

    internal object HotPostHeaderDiffCallback : DiffUtil.ItemCallback<HotPostHeaderItem>() {
        override fun areItemsTheSame(oldItem: HotPostHeaderItem, newItem: HotPostHeaderItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HotPostHeaderItem, newItem: HotPostHeaderItem) =
            oldItem.equals(newItem)
    }
}

object HotPostHeaderItem




