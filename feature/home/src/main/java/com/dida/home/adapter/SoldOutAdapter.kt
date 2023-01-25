package com.dida.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.model.nav.home.SoldOut
import com.dida.home.HomeActionHandler
import com.dida.home.R
import com.dida.home.databinding.HolderSoldoutBinding

class SoldOutAdapter(
    private val eventListener: HomeActionHandler
): ListAdapter<SoldOut, SoldOutAdapter.ViewHolder>(SoldOutItemDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderSoldoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_soldout,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderSoldoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SoldOut) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object SoldOutItemDiffCallback : DiffUtil.ItemCallback<SoldOut>() {
        override fun areItemsTheSame(oldItem: SoldOut, newItem: SoldOut) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: SoldOut, newItem: SoldOut) =
            oldItem.equals(newItem)
    }
}
