package com.dida.android.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.databinding.HolderMypageUserCardsBinding
import com.dida.domain.model.nav.home.Collection
import com.dida.domain.model.nav.home.HotSeller
import com.dida.domain.model.nav.mypage.UserCardsResponseModel

class RecentNftAdapter(
    private val clickUnit: (nftId: Int) ->Unit
): RecyclerView.Adapter<RecentNftAdapter.ViewHolder>() {
    private val itemList = ArrayList<UserCardsResponseModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = HolderMypageUserCardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderModel = itemList[position]
        holder.bind(holderModel)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val viewDataBinding: HolderMypageUserCardsBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(holderModel: UserCardsResponseModel) {
            viewDataBinding.holderModel = holderModel

            itemView.setOnClickListener {
                clickUnit(holderModel.cardId)
            }
        }
    }

    fun addAll(items: List<UserCardsResponseModel>) {
        itemList.clear()
        itemList.addAll(items)
        this.notifyDataSetChanged()
    }
}