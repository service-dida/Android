package com.dida.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.home.HomeActionHandler
import com.dida.home.R
import com.dida.home.databinding.HolderHomeEmptyBinding

class HomeEmptyAdapter(
    val eventListener: HomeActionHandler
) : ListAdapter<HomeEmptyItem, HotSellerEmptyViewHolder>(HomeEmptyItemDiffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSellerEmptyViewHolder {
        val viewDataBinding: HolderHomeEmptyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_home_empty,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return HotSellerEmptyViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: HotSellerEmptyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind() }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_home_empty

}

class HotSellerEmptyViewHolder(
    private val binding: HolderHomeEmptyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.executePendingBindings()
    }
}

internal object HomeEmptyItemDiffCallback : DiffUtil.ItemCallback<HomeEmptyItem>() {
    override fun areItemsTheSame(oldItem: HomeEmptyItem, newItem: HomeEmptyItem) =
        oldItem.index == newItem.index

    override fun areContentsTheSame(oldItem: HomeEmptyItem, newItem: HomeEmptyItem) =
        oldItem == newItem
}

data class HomeEmptyItem(
    val index: Int
)


