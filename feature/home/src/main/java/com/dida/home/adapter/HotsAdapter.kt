package com.dida.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.main.model.HotItem
import com.dida.home.HomeActionHandler
import com.dida.home.R
import com.dida.home.databinding.HolderHotsBinding

class HotsAdapter(
    private val eventListener: HomeActionHandler
) : ListAdapter<HotItem, HotsAdapter.ViewHolder>(HotsItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderHotsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_hots,
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

    override fun getItemViewType(position: Int): Int = R.layout.holder_hots

    class ViewHolder(private val binding: HolderHotsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HotItem) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object HotsItemDiffCallback : DiffUtil.ItemCallback<HotItem>() {
        override fun areItemsTheSame(oldItem: HotItem, newItem: HotItem) =
            oldItem.nftId == newItem.nftId

        override fun areContentsTheSame(oldItem: HotItem, newItem: HotItem) =
            oldItem == newItem
    }
}
