package com.dida.settings.hidelist


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.main.model.HideNft
import com.dida.settings.R
import com.dida.settings.databinding.HolderHideListItemBinding

// TOOD : 숨김 카드 목록 관련 수정
class HideListAdapter(
    private val eventListener: HideListActionHandler
): ListAdapter<HideNft, HideListAdapter.ViewHolder>(HideListItemDiffCallback) {

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

    override fun getItemId(position: Int): Long = getItem(position).nftId * -1

    override fun getItemViewType(position: Int): Int = R.layout.holder_hide_list_item

    class ViewHolder(private val binding: HolderHideListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HideNft) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object HideListItemDiffCallback : DiffUtil.ItemCallback<HideNft>() {
        override fun areItemsTheSame(oldItem: HideNft, newItem: HideNft) =
            oldItem.nftId == newItem.nftId

        override fun areContentsTheSame(oldItem: HideNft, newItem: HideNft) =
            oldItem == newItem
    }
}
