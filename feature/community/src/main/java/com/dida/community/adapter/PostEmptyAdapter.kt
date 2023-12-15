package com.dida.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.community.databinding.HolderPostEmptyBinding

class PostEmptyAdapter: ListAdapter<PostEmptyItem, PostEmptyAdapter.ViewHolder>(PostEmptyDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderPostEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind() }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    class ViewHolder(private val binding: HolderPostEmptyBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.executePendingBindings()
        }
    }

    internal object PostEmptyDiffCallback : DiffUtil.ItemCallback<PostEmptyItem>() {
        override fun areItemsTheSame(oldItem: PostEmptyItem, newItem: PostEmptyItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: PostEmptyItem, newItem: PostEmptyItem) =
            oldItem.equals(newItem)
    }
}

object PostEmptyItem




