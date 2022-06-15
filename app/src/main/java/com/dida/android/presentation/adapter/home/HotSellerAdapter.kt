package com.dida.android.presentation.adapter.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.domain.model.nav.home.HotSeller

class HotSellerAdapter() :
    RecyclerView.Adapter<HotSellerHolderPage>(){
    var datas = ArrayList<HotSeller>()

    private val itemList = ArrayList<HotSeller>()

    interface OnItemClickEventListener {
        fun onItemClick(a_view: View?, a_position: Int)
    }

    private var nItemClickListener: OnItemClickEventListener? = null

    fun nextItemClickListener(a_listener: OnItemClickEventListener) {
        nItemClickListener = a_listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSellerHolderPage {
        val context: Context = parent.context
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.holder_hotseller, parent, false)
        return HotSellerHolderPage(view, context, nItemClickListener!!)
    }

    override fun onBindViewHolder(holder: HotSellerHolderPage, position: Int) {
        if (holder is HotSellerHolderPage) {
            val viewHolder: HotSellerHolderPage = holder as HotSellerHolderPage
            viewHolder.onBind(itemList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addItem(item: HotSeller) {
        itemList.add(item)
    }

    fun getItem(position: Int): HotSeller {
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