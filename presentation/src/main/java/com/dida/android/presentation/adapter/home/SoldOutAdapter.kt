package com.dida.android.presentation.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.databinding.HolderSoldoutBinding
import com.dida.domain.model.nav.home.SoldOut

class SoldOutAdapter() :
    RecyclerView.Adapter<SoldOutAdapter.ViewHolder>(){
    var datas = ArrayList<SoldOut>()

    private val itemList = ArrayList<SoldOut>()

    interface OnItemClickEventListener {
        fun onItemClick(a_position: Int)
    }

    private var nItemClickListener: OnItemClickEventListener? = null

    fun nextItemClickListener(a_listener: OnItemClickEventListener) {
        nItemClickListener = a_listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = HolderSoldoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderModel = itemList[position]
        holder.bind(holderModel)
        holder.itemView.setOnClickListener {
            nItemClickListener!!.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val viewDataBinding: HolderSoldoutBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(holderModel: SoldOut) {
            viewDataBinding.holderModel = holderModel
        }
    }

    fun addItem(item: SoldOut) {
        itemList.add(item)
    }
}