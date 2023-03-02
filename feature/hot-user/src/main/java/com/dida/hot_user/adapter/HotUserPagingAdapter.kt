package com.dida.hot_user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.adapter.RecentNftAdapter
import com.dida.domain.model.nav.home.HotUser
import com.dida.hot_user.HotUserActionHandler
import com.dida.hot_user.R
import com.dida.hot_user.databinding.HolderHotUserBinding

class HotUserPagingAdapter(
    private val eventListener: HotUserActionHandler
): PagingDataAdapter<HotUser, HotUserPagingAdapter.ViewHolder>(HotUserItemDiffCallback) {

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
        fun bind(item: HotUser) {
            binding.holderModel = item

            binding.userCardRecyclerview.apply {
                adapter = UserCardAdapter().apply { submitList(item.cardUrls) }
                layoutManager = GridLayoutManager(context, 5)
            }
            binding.executePendingBindings()
        }
    }

    internal object HotUserItemDiffCallback : DiffUtil.ItemCallback<HotUser>() {
        override fun areItemsTheSame(oldItem: HotUser, newItem: HotUser) =
            oldItem.userId == newItem.userId

        override fun areContentsTheSame(oldItem: HotUser, newItem: HotUser) =
            oldItem == newItem
    }
}
