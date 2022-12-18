package com.dida.android.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.databinding.HolderMypageUserCardsBinding
import com.dida.android.util.NftActionHandler
import com.dida.domain.model.nav.mypage.UserNft

class RecentNftAdapter(
    private val eventListener: NftActionHandler
): ListAdapter<UserNft, RecentNftAdapter.ViewHolder>(RecentNftItemDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderMypageUserCardsBinding = DataBindingUtil.inflate<HolderMypageUserCardsBinding?>(
            LayoutInflater.from(parent.context),
            R.layout.holder_mypage_user_cards,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderMypageUserCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserNft) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object RecentNftItemDiffCallback : DiffUtil.ItemCallback<UserNft>() {
        override fun areItemsTheSame(oldItem: UserNft, newItem: UserNft) =
            oldItem.cardId == newItem.cardId && oldItem.liked == newItem.liked

        override fun areContentsTheSame(oldItem: UserNft, newItem: UserNft) =
            oldItem == newItem
    }
}