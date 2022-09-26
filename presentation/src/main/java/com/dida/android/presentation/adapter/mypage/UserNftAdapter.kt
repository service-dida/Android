package com.dida.android.presentation.adapter.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.databinding.HolderHotsellerBinding
import com.dida.android.databinding.HolderMypageUserCardsBinding
import com.dida.android.presentation.views.nav.home.HomeActionHandler
import com.dida.android.presentation.views.nav.mypage.MypageActionHandler
import com.dida.android.util.NftActionHandler
import com.dida.domain.model.nav.home.HotSeller
import com.dida.domain.model.nav.mypage.UserCardsResponseModel

class UserNftAdapter(
    private val eventListener: NftActionHandler
): ListAdapter<UserCardsResponseModel, UserNftAdapter.ViewHolder>(RecentNftItemDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderMypageUserCardsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_mypage_user_cards,
            parent,
            false
        )
        viewDataBinding.root.setOnClickListener {
            eventListener.onNftItemClicked(viewDataBinding.holderModel!!.cardId)
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderMypageUserCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserCardsResponseModel) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object RecentNftItemDiffCallback : DiffUtil.ItemCallback<UserCardsResponseModel>() {
        override fun areItemsTheSame(oldItem: UserCardsResponseModel, newItem: UserCardsResponseModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: UserCardsResponseModel, newItem: UserCardsResponseModel) =
            oldItem.equals(newItem)
    }
}