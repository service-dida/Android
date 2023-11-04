package com.dida.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.main.model.Sort
import com.dida.mypage.MypageActionHandler
import com.dida.mypage.R
import com.dida.mypage.databinding.HolderMyPageSortBinding

class MyPageSortAdapter(
    private val eventListener: MypageActionHandler
) : ListAdapter<Sort, MyPageSortAdapter.ViewHolder>(MyPageSortDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderMyPageSortBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_my_page_sort,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(item = it)
        }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    class ViewHolder(private val binding: HolderMyPageSortBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Sort) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object MyPageSortDiffCallback : DiffUtil.ItemCallback<Sort>() {
        override fun areItemsTheSame(oldItem: Sort, newItem: Sort) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Sort, newItem: Sort) =
            oldItem == newItem
    }
}
