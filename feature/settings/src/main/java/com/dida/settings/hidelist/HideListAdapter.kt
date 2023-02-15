package com.dida.settings.hidelist


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.model.nav.hide.CardHideList
import com.dida.settings.R
import com.dida.settings.databinding.HolderHideListItemBinding

class HideListAdapter(
    private val eventListener: HideListActionHandler
): ListAdapter<CardHideList, HideListAdapter.ViewHolder>(HideListItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderHideListItemBinding = DataBindingUtil.inflate<HolderHideListItemBinding?>(
            LayoutInflater.from(parent.context),
            R.layout.holder_hide_list_item,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderHideListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CardHideList) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object HideListItemDiffCallback : DiffUtil.ItemCallback<CardHideList>() {
        override fun areItemsTheSame(oldItem: CardHideList, newItem: CardHideList) =
            oldItem.cardId == newItem.cardId

        override fun areContentsTheSame(oldItem: CardHideList, newItem: CardHideList) =
            oldItem == newItem
    }
}
