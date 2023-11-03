package com.dida.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.actionhandler.NftActionHandler
import com.dida.mypage.databinding.HolderMyPageEmptyBinding

class MyPageEmptyAdapter(
    val eventListener: NftActionHandler
): ListAdapter<MyPageEmptyItem, MyPageEmptyAdapter.ViewHolder>(MyPageEmptyDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderMyPageEmptyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_my_page_empty,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind() }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    class ViewHolder(private val binding: HolderMyPageEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.executePendingBindings()
        }
    }

    internal object MyPageEmptyDiffCallback : DiffUtil.ItemCallback<MyPageEmptyItem>() {
        override fun areItemsTheSame(oldItem: MyPageEmptyItem, newItem: MyPageEmptyItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: MyPageEmptyItem, newItem: MyPageEmptyItem) =
            oldItem.equals(newItem)
    }
}

object MyPageEmptyItem




