package com.dida.android.presentation.adapter.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.databinding.HolderMypageUserCardsBinding
import com.dida.domain.model.nav.mypage.UserCardsResponseModel

class MyPageUserCardsRecyclerViewAdapter(private val modelList: List<UserCardsResponseModel>, private val clickUnit: (nftId: Long) ->Unit): RecyclerView.Adapter<MyPageUserCardsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = HolderMypageUserCardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderModel = modelList[position]
        holder.bind(holderModel)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    inner class ViewHolder(val viewDataBinding: HolderMypageUserCardsBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(holderModel: UserCardsResponseModel) {
            viewDataBinding.holderModel = holderModel

            itemView.setOnClickListener {
                clickUnit(holderModel.cardId)
            }
        }
    }
}