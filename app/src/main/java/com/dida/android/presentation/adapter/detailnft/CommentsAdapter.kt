package com.dida.android.presentation.adapter.detailnft

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.databinding.HolderCommentsBinding
import com.dida.android.domain.model.nav.detailnft.Comments

class CommentsAdapter() :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>(){
    var datas = ArrayList<Comments>()

    private val itemList = ArrayList<Comments>()

    interface OnItemClickEventListener {
        fun onItemClick(a_view: View?, a_position: Int)
    }

    private var nItemClickListener: OnItemClickEventListener? = null

    fun nextItemClickListener(a_listener: OnItemClickEventListener) {
        nItemClickListener = a_listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = HolderCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderModel = itemList[position]
        holder.bind(holderModel)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val viewDataBinding: HolderCommentsBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(holderModel: Comments) {
            viewDataBinding.holderModel = holderModel
        }
    }

    fun addItem(item: Comments) {
        itemList.add(item)
    }
}