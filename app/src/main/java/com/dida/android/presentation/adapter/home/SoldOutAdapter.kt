package com.dida.android.presentation.adapter.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.domain.model.nav.home.Hots
import com.dida.android.domain.model.nav.home.SoldOut

class SoldOutAdapter() :
    RecyclerView.Adapter<SoldOutHolderPage>(){
    var datas = ArrayList<SoldOut>()

    private val itemList = ArrayList<SoldOut>()

    interface OnItemClickEventListener {
        fun onItemClick(a_view: View?, a_position: Int)
    }

    private var nItemClickListener: OnItemClickEventListener? = null

    fun nextItemClickListener(a_listener: OnItemClickEventListener) {
        nItemClickListener = a_listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoldOutHolderPage {
        val context: Context = parent.context
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.soldout_item, parent, false)
        return SoldOutHolderPage(view, context, nItemClickListener!!)
    }

    override fun onBindViewHolder(holder: SoldOutHolderPage, position: Int) {
        if (holder is SoldOutHolderPage) {
            val viewHolder: SoldOutHolderPage = holder as SoldOutHolderPage
            viewHolder.onBind(itemList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addItem(item: SoldOut) {
        itemList.add(item)
    }

    fun getItem(position: Int): SoldOut {
        return itemList[position]
    }

    fun deleteItem(position: Int) {
        itemList.removeAt(position)
    }

    fun clear() {
        itemList.clear()
        this.notifyDataSetChanged()
    }
}