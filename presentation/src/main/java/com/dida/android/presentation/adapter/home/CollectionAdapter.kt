package com.dida.android.presentation.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.databinding.HolderCollectionBinding
import com.dida.android.databinding.HolderHotsBinding
import com.dida.android.databinding.HolderHotsellerBinding
import com.dida.android.presentation.views.nav.home.HomeActionHandler
import com.dida.domain.model.nav.home.Collection
import com.dida.domain.model.nav.home.HotSeller
import com.dida.domain.model.nav.home.Hots

class CollectionAdapter(
    private val eventListener: HomeActionHandler
) : ListAdapter<Collection, CollectionAdapter.ViewHolder>(CollectionItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderCollectionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_collection,
            parent,
            false
        )
        viewDataBinding.root.setOnClickListener {
            eventListener.onCollectionItemClicked(viewDataBinding.holderModel!!.userId)
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Collection) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object CollectionItemDiffCallback : DiffUtil.ItemCallback<Collection>() {
        override fun areItemsTheSame(oldItem: Collection, newItem: Collection) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Collection, newItem: Collection) =
            oldItem.equals(newItem)
    }
}