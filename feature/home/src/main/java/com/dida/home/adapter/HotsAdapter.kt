package com.dida.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.model.nav.home.Hots
import com.dida.home.HomeActionHandler
import com.dida.home.R
import com.dida.home.databinding.HolderHotsBinding

class HotsAdapter(
    private val eventListener: HomeActionHandler
) : ListAdapter<Hots, HotsAdapter.ViewHolder>(HotsItemDiffCallback){

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

    override fun getItemId(position: Int): Long = getItem(position).cardId.toLong() * -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_hots

    class ViewHolder(private val binding: HolderHotsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Hots) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object HotsItemDiffCallback : DiffUtil.ItemCallback<Hots>() {
        override fun areItemsTheSame(oldItem: Hots, newItem: Hots) =
            oldItem.cardId == newItem.cardId

        override fun areContentsTheSame(oldItem: Hots, newItem: Hots) =
            oldItem == newItem
    }
}
