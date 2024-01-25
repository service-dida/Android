package com.dida.common.adapter

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.R
import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.databinding.HolderUserCardContainerBinding
import com.dida.domain.main.model.CommonProfileNft

class UserCardContainerAdapter(
    private val eventListener: NftActionHandler
) : ListAdapter<UserCardContainerItem, UserCardContainerAdapter.ViewHolder>(UserCardContainerItemDiffCallback) {

    private var changeItemPool: SparseArray<UserCardContainerItem> = SparseArray()

    init { setHasStableIds(true) }

    override fun submitList(list: List<UserCardContainerItem>?) {
        changeItemPool.clear()
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderUserCardContainerBinding =
            DataBindingUtil.inflate<HolderUserCardContainerBinding>(
                LayoutInflater.from(parent.context),
                R.layout.holder_user_card_container,
                parent,
                false
            )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            (payloads.firstOrNull() as? UserCardContainerItem)?.let {
                changeItemPool.put(position, it)
                holder.bind(it.items)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = changeItemPool[position] ?: getItem(position)
        item?.let { holder.bind(it.items) }
    }

    class ViewHolder(private val binding: HolderUserCardContainerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: List<CommonProfileNft>) {
            binding.items = items
            binding.executePendingBindings()
        }
    }

    internal object UserCardContainerItemDiffCallback : DiffUtil.ItemCallback<UserCardContainerItem>() {
        override fun areItemsTheSame(oldItem: UserCardContainerItem, newItem: UserCardContainerItem) =
            oldItem.items.zip(newItem.items).all { (old, new) ->
                (old.nftInfo.nftId == new.nftInfo.nftId) && (old.liked == new.liked)
            }

        override fun areContentsTheSame(oldItem: UserCardContainerItem, newItem: UserCardContainerItem) =
            oldItem.items == newItem.items
    }
}

data class UserCardContainerItem(
    val items: List<CommonProfileNft>
)
