package com.dida.hot_user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.main.model.HotSellerPage
import com.dida.hot_user.HotUserActionHandler
import com.dida.hot_user.R
import com.dida.hot_user.databinding.HolderHotUserBinding

class HotUserAdapter(
    private val eventListener: HotUserActionHandler
): ListAdapter<HotSellerPage, HotUserAdapter.ViewHolder>(HotUserItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderHotUserBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_hot_user,
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

    class ViewHolder(private val binding: HolderHotUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HotSellerPage) {
            binding.holderModel = item

            binding.userCardRecyclerview.apply {
                adapter = UserCardAdapter().apply { submitList(item.nftImgUrl) }
                layoutManager = GridLayoutManager(context, 5)
            }
            binding.executePendingBindings()
        }
    }

    internal object HotUserItemDiffCallback : DiffUtil.ItemCallback<HotSellerPage>() {
        override fun areItemsTheSame(oldItem: HotSellerPage, newItem: HotSellerPage) =
            oldItem.memberInfo.memberId == newItem.memberInfo.memberId && oldItem.memberInfo.followed == oldItem.memberInfo.followed

        override fun areContentsTheSame(oldItem: HotSellerPage, newItem: HotSellerPage) =
            oldItem == newItem
    }
}
