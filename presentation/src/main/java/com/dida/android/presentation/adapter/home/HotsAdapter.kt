package com.dida.android.presentation.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.databinding.HolderHotsBinding
import com.dida.domain.model.nav.home.Hots

class HotsAdapter() :
    RecyclerView.Adapter<HotsAdapter.ViewHolder>(){

    private val itemList = ArrayList<Hots>()

    interface OnItemClickEventListener {
        fun onItemClick(a_view: View?, a_position: Int)
    }

    private var nItemClickListener: OnItemClickEventListener? = null

    fun nextItemClickListener(a_listener: OnItemClickEventListener) {
        nItemClickListener = a_listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = HolderHotsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val holderModel = itemList[position]
        holder.bind(holderModel)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(val viewDataBinding: HolderHotsBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(holderModel: Hots) {
            viewDataBinding.holderModel = holderModel
        }
    }

    fun addAll(items: List<Hots>) {
        itemList.clear()
        itemList.addAll(items)
        this.notifyDataSetChanged()
    }
}