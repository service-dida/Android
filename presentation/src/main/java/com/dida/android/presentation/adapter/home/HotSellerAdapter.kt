package com.dida.android.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.databinding.HolderHotsellerBinding
import com.dida.android.presentation.views.nav.home.HomeActionHandler
import com.dida.domain.model.nav.home.HotSeller

class HotSellerAdapter(
    private val eventListener: HomeActionHandler
) : ListAdapter<HotSeller, HotSellerAdapter.ViewHolder>(HotSellerItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderHotsellerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_hotseller,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderHotsellerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HotSeller) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object HotSellerItemDiffCallback : DiffUtil.ItemCallback<HotSeller>() {
        override fun areItemsTheSame(oldItem: HotSeller, newItem: HotSeller) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HotSeller, newItem: HotSeller) =
            oldItem.equals(newItem)
    }
}