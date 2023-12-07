package com.dida.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.util.context
import com.dida.community.HotCardActionHandler
import com.dida.community.R
import com.dida.community.databinding.HolderHotPostBinding
import com.dida.domain.main.model.HotPost

class HotPostAdapter(
    private val eventListener: HotCardActionHandler
) : ListAdapter<HotPost, HotPostAdapter.ViewHolder>(HotPostDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderHotPostBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_hot_post,
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

    override fun getItemViewType(position: Int): Int = R.layout.holder_hot_post

    class ViewHolder(private val binding: HolderHotPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HotPost) {
            binding.holderModel = item
            binding.nftImageView.setColorFilter(context.getColor(com.dida.common.R.color.dim_black_50))
            binding.executePendingBindings()
        }
    }

    internal object HotPostDiffCallback : DiffUtil.ItemCallback<HotPost>() {
        override fun areItemsTheSame(oldItem: HotPost, newItem: HotPost) =
            oldItem.nftId == newItem.nftId

        override fun areContentsTheSame(oldItem: HotPost, newItem: HotPost) =
            oldItem == newItem
    }
}
