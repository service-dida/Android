package com.dida.recent_nft.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.databinding.HolderMypageUserCardsBinding
import com.dida.common.actionhandler.NftActionHandler
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.recent_nft.R

class CardPagingAdapter(
    private val eventListener: NftActionHandler
): PagingDataAdapter<UserNft, CardPagingAdapter.ViewHolder>(CardItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderMypageUserCardsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            com.dida.common.R.layout.holder_mypage_user_cards,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemViewType(position: Int): Int = com.dida.common.R.layout.holder_mypage_user_cards

    class ViewHolder(private val binding: HolderMypageUserCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserNft) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object CardItemDiffCallback : DiffUtil.ItemCallback<UserNft>() {
        override fun areItemsTheSame(oldItem: UserNft, newItem: UserNft) =
            oldItem.cardId == newItem.cardId

        override fun areContentsTheSame(oldItem: UserNft, newItem: UserNft) =
            oldItem == newItem
    }
}
