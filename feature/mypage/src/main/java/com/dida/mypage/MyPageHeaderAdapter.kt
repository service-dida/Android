package com.dida.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.main.model.CommonProfile
import com.dida.mypage.databinding.HolderMyPageHeaderBinding

class MyPageHeaderAdapter(
    private val eventListener: MypageActionHandler
) : ListAdapter<CommonProfile, MyPageHeaderAdapter.ViewHolder>(MyPageHeaderDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderMyPageHeaderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_my_page_header,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(item = it) }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    class ViewHolder(private val binding: HolderMyPageHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommonProfile) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object MyPageHeaderDiffCallback : DiffUtil.ItemCallback<CommonProfile>() {
        override fun areItemsTheSame(oldItem: CommonProfile, newItem: CommonProfile) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CommonProfile, newItem: CommonProfile) =
            oldItem == newItem
    }
}
