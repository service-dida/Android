package com.dida.android.presentation.adapter.detailnft

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.R
import com.dida.android.domain.model.nav.detailnft.Comments

class CommentsAdapter() :
    RecyclerView.Adapter<CommentsHolderPage>(){
    var datas = ArrayList<Comments>()

    private val itemList = ArrayList<Comments>()

    interface OnItemClickEventListener {
        fun onItemClick(a_view: View?, a_position: Int)
    }

    private var nItemClickListener: OnItemClickEventListener? = null

    fun nextItemClickListener(a_listener: OnItemClickEventListener) {
        nItemClickListener = a_listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsHolderPage {
        val context: Context = parent.context
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.holder_comments, parent, false)
        return CommentsHolderPage(view, context, nItemClickListener!!)
    }

    override fun onBindViewHolder(holder: CommentsHolderPage, position: Int) {
        if (holder is CommentsHolderPage) {
            val viewHolder: CommentsHolderPage = holder as CommentsHolderPage
            viewHolder.onBind(itemList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addItem(item: Comments) {
        itemList.add(item)
    }

    fun getItem(position: Int): Comments {
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