package com.dida.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.R
import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.databinding.HolderMypageUserCardsBinding
import com.dida.domain.model.main.UserNft

class UserCardAdapter(
    private val eventListener: NftActionHandler
) : PagingDataAdapter<UserNft, UserCardAdapter.ViewHolder>(RecentNftItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderMypageUserCardsBinding =
            DataBindingUtil.inflate<HolderMypageUserCardsBinding?>(
                LayoutInflater.from(parent.context),
                R.layout.holder_mypage_user_cards,
                parent,
                false
            )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemViewType(position: Int): Int = R.layout.holder_mypage_user_cards

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
