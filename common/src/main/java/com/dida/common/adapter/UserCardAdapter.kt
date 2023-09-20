package com.dida.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.R
import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.databinding.HolderMypageUserCardsBinding
import com.dida.domain.main.model.CommonProfileNft

class UserCardAdapter(
    private val eventListener: NftActionHandler
) : ListAdapter<CommonProfileNft, UserCardAdapter.ViewHolder>(RecentNftItemDiffCallback) {

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

    fun changeNftLike(nftId: Long) {
        val index = findIndexFromNftId(nftId)
        currentList[index].apply {
            this.liked = !this.liked
        }
        this@UserCardAdapter.notifyItemChanged(index)
    }

    private fun findIndexFromNftId(nftId: Long): Int {
        return currentList.indexOfFirst { (it.nftInfo.nftId == nftId) }
    }

    class ViewHolder(private val binding: HolderMypageUserCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommonProfileNft) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object RecentNftItemDiffCallback : DiffUtil.ItemCallback<CommonProfileNft>() {
        override fun areItemsTheSame(oldItem: CommonProfileNft, newItem: CommonProfileNft) =
            oldItem.nftInfo.nftId == newItem.nftInfo.nftId && oldItem.liked == newItem.liked

        override fun areContentsTheSame(oldItem: CommonProfileNft, newItem: CommonProfileNft) =
            oldItem == newItem
    }
}
