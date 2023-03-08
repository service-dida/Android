package com.dida.settings.hidelist


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.model.nav.hide.HideCard
import com.dida.settings.R
import com.dida.settings.databinding.HolderHideListItemBinding

class HideListAdapter(
    private val eventListener: HideListActionHandler
): ListAdapter<HideCard, HideListAdapter.ViewHolder>(HideListItemDiffCallback) {

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

    override fun getItemId(position: Int): Long = getItem(position).cardId.toLong() * -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_hide_list_item

    class ViewHolder(private val binding: HolderHideListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HideCard) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object HideListItemDiffCallback : DiffUtil.ItemCallback<HideCard>() {
        override fun areItemsTheSame(oldItem: HideCard, newItem: HideCard) =
            oldItem.cardId == newItem.cardId

        override fun areContentsTheSame(oldItem: HideCard, newItem: HideCard) =
            oldItem == newItem
    }
}
