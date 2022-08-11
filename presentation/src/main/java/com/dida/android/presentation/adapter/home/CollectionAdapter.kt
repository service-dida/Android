package com.dida.android.presentation.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.databinding.HolderCollectionBinding
import com.dida.domain.model.nav.home.Collection

class CollectionAdapter() :
    RecyclerView.Adapter<CollectionAdapter.ViewHolder>(){
    private val itemList = ArrayList<Collection>()

    interface OnItemClickEventListener {
        fun onItemClick(a_view: View?, a_position: Int)
    }

    private var nItemClickListener: OnItemClickEventListener? = null

    fun nextItemClickListener(a_listener: OnItemClickEventListener) {
        nItemClickListener = a_listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = HolderCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderModel = itemList[position]
        holder.bind(holderModel)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val viewDataBinding: HolderCollectionBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(holderModel: Collection) {
            viewDataBinding.holderModel = holderModel
        }
    }

    fun addAll(items: List<Collection>) {
        itemList.clear()
        itemList.addAll(items)
        this.notifyDataSetChanged()
    }
}