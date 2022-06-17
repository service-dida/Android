package com.dida.android.presentation.adapter.detailnft

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.domain.model.nav.detailnft.Community

class CommunityAdapter() :
    RecyclerView.Adapter<CommunityHolderPage>(){
    var datas = ArrayList<Community>()

    private val itemList = ArrayList<Community>()

    interface OnItemClickEventListener {
        fun onItemClick(a_view: View?, a_position: Int)
    }

    private var nItemClickListener: OnItemClickEventListener? = null

    fun nextItemClickListener(a_listener: OnItemClickEventListener) {
        nItemClickListener = a_listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityHolderPage {
        val context: Context = parent.context
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.holder_community, parent, false)
        return CommunityHolderPage(view, context, nItemClickListener!!)
    }

    override fun onBindViewHolder(holder: CommunityHolderPage, position: Int) {
        if (holder is CommunityHolderPage) {
            val viewHolder: CommunityHolderPage = holder as CommunityHolderPage
            viewHolder.onBind(itemList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addItem(item: Community) {
        itemList.add(item)
    }

    fun getItem(position: Int): Community {
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