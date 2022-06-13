package com.dida.android.presentation.adapter.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.domain.model.nav.home.Collection
import com.dida.android.domain.model.nav.home.Hots
import com.dida.android.domain.model.nav.home.SoldOut

class CollectionAdapter() :
    RecyclerView.Adapter<CollectionHolderPage>(){
    var datas = ArrayList<Collection>()

    private val itemList = ArrayList<Collection>()

    interface OnItemClickEventListener {
        fun onItemClick(a_view: View?, a_position: Int)
    }

    private var nItemClickListener: OnItemClickEventListener? = null

    fun nextItemClickListener(a_listener: OnItemClickEventListener) {
        nItemClickListener = a_listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionHolderPage {
        val context: Context = parent.context
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.collection_item, parent, false)
        return CollectionHolderPage(view, context, nItemClickListener!!)
    }

    override fun onBindViewHolder(holder: CollectionHolderPage, position: Int) {
        if (holder is CollectionHolderPage) {
            val viewHolder: CollectionHolderPage = holder as CollectionHolderPage
            viewHolder.onBind(itemList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addItem(item: Collection) {
        itemList.add(item)
    }

    fun getItem(position: Int): Collection {
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