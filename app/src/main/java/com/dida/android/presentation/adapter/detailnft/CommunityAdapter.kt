package com.dida.android.presentation.adapter.detailnft

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.databinding.HolderCommentsBinding
import com.dida.android.databinding.HolderCommunityBinding
import com.dida.android.domain.model.nav.detailnft.Comments
import com.dida.android.domain.model.nav.detailnft.Community

class CommunityAdapter() :
    RecyclerView.Adapter<CommunityAdapter.ViewHolder>(){
    var datas = ArrayList<Community>()
    val adapter = CommentsAdapter()

    private val itemList = ArrayList<Community>()

    interface OnItemClickEventListener {
        fun onItemClick(a_view: View?, a_position: Int)
    }

    private var nItemClickListener: OnItemClickEventListener? = null

    fun nextItemClickListener(a_listener: OnItemClickEventListener) {
        nItemClickListener = a_listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = HolderCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderModel = itemList[position]
        itemList[position].Comments.forEach { item ->
            adapter.addItem(item)
        }
        holder.bind(holderModel)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val viewDataBinding: HolderCommunityBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(holderModel: Community) {
            viewDataBinding.holderModel = holderModel
            viewDataBinding.commentRecycler.adapter = adapter
            viewDataBinding.executePendingBindings()
        }
    }

    fun addItem(item: Community) {
        itemList.add(item)
    }
}