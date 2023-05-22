package com.dida.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.home.HomeActionHandler
import com.dida.home.R
import com.dida.home.databinding.HolderHotsellerEmptyBinding
import com.dida.home.databinding.HolderHotsellerMoreBinding

class HotSellerEmptyAdapter(
    val eventListener: HomeActionHandler
) : ListAdapter<HotSellerEmptyItem, HotSellerEmptyViewHolder>(HotSellerEmptyItemDiffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSellerEmptyViewHolder {
        val viewDataBinding: HolderHotsellerEmptyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_hotseller_empty,
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

    override fun getItemViewType(position: Int): Int = R.layout.holder_hotseller_empty

}

class HotSellerEmptyViewHolder(
    private val binding: HolderHotsellerEmptyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.executePendingBindings()
    }
}

internal object HotSellerEmptyItemDiffCallback : DiffUtil.ItemCallback<HotSellerEmptyItem>() {
    override fun areItemsTheSame(oldItem: HotSellerEmptyItem, newItem: HotSellerEmptyItem) =
        oldItem.index == newItem.index

    override fun areContentsTheSame(oldItem: HotSellerEmptyItem, newItem: HotSellerEmptyItem) =
        oldItem == newItem
}

data class HotSellerEmptyItem(
    val index: Int
)


