package com.dida.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.home.HomeActionHandler
import com.dida.home.R
import com.dida.home.databinding.HolderCollectionMoreBinding
import com.dida.home.databinding.HolderHotsellerMoreBinding

class CollectionMoreAdapter(
    val eventListener: HomeActionHandler
) : ListAdapter<CollectionMoreItem, CollectionMoreViewHolder>(CollectionMoreItemDiffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionMoreViewHolder {
        val viewDataBinding: HolderCollectionMoreBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_collection_more,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return CollectionMoreViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: CollectionMoreViewHolder, position: Int) {
        getItem(position)?.let { holder.bind() }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_collection_more

}

class CollectionMoreViewHolder(
    private val binding: HolderCollectionMoreBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.executePendingBindings()
    }
}

internal object CollectionMoreItemDiffCallback : DiffUtil.ItemCallback<CollectionMoreItem>() {
    override fun areItemsTheSame(oldItem: CollectionMoreItem, newItem: CollectionMoreItem) =
        oldItem.index == newItem.index

    override fun areContentsTheSame(oldItem: CollectionMoreItem, newItem: CollectionMoreItem) =
        oldItem == newItem
}

data class CollectionMoreItem(
    val index: Int
)


