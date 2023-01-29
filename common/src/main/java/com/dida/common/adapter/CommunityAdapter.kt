package com.dida.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.R
import com.dida.common.databinding.HolderCommunityBinding
import com.dida.common.util.CommunityActionHandler
import com.dida.domain.model.nav.detailnft.Community

class CommunityAdapter(
    private val eventListener: CommunityActionHandler
) : ListAdapter<Community, CommunityAdapter.ViewHolder>(CommuityDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderCommunityBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_community,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderCommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Community) {
            binding.holderModel = item
            val adapter = CommentsAdapter()
            adapter.submitList(item.Comments)
            binding.commentRecycler.adapter = adapter
            binding.executePendingBindings()
        }
    }

    internal object CommuityDiffCallback : DiffUtil.ItemCallback<Community>() {
        override fun areItemsTheSame(oldItem: Community, newItem: Community) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Community, newItem: Community) =
            oldItem.equals(newItem)
    }
}