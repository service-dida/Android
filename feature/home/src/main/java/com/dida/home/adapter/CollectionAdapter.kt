package com.dida.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.main.model.HotMember
import com.dida.home.HomeActionHandler
import com.dida.home.R
import com.dida.home.databinding.HolderCollectionBinding

class CollectionAdapter(
    private val eventListener: HomeActionHandler
) : ListAdapter<HotMember, CollectionAdapter.ViewHolder>(CollectionItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderCollectionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_collection,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long = getItem(position).memberId * -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_collection

    class ViewHolder(private val binding: HolderCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HotMember) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object CollectionItemDiffCallback : DiffUtil.ItemCallback<HotMember>() {
        override fun areItemsTheSame(oldItem: HotMember, newItem: HotMember) =
            oldItem.memberId == newItem.memberId

        override fun areContentsTheSame(oldItem: HotMember, newItem: HotMember) =
            oldItem == newItem
    }
}
