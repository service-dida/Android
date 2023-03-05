package com.dida.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.home.HomeActionHandler
import com.dida.home.R
import com.dida.home.databinding.HolderHotsellerMoreBinding

class HotSellerMoreAdapter(
    val eventListener: HomeActionHandler
) : ListAdapter<HotSellerMoreItem, HotSellerMoreViewHolder>(HotSellerMoreItemDiffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSellerMoreViewHolder {
        val viewDataBinding: HolderHotsellerMoreBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_hotseller_more,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return HotSellerMoreViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: HotSellerMoreViewHolder, position: Int) {
        getItem(position)?.let { holder.bind() }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_hotseller_more

}

class HotSellerMoreViewHolder(
    private val binding: HolderHotsellerMoreBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.executePendingBindings()
    }
}

internal object HotSellerMoreItemDiffCallback : DiffUtil.ItemCallback<HotSellerMoreItem>() {
    override fun areItemsTheSame(oldItem: HotSellerMoreItem, newItem: HotSellerMoreItem) =
        oldItem.index == newItem.index

    override fun areContentsTheSame(oldItem: HotSellerMoreItem, newItem: HotSellerMoreItem) =
        oldItem == newItem
}

data class HotSellerMoreItem(
    val index: Int
)


