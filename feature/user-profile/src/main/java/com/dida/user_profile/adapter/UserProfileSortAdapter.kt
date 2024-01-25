package com.dida.user_profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.main.model.Sort
import com.dida.user_profile.R
import com.dida.user_profile.UserProfileActionHandler
import com.dida.user_profile.databinding.HolderUserProfileSortBinding

class UserProfileSortAdapter(
    private val eventListener: UserProfileActionHandler
) : ListAdapter<Sort, UserProfileSortAdapter.ViewHolder>(UserProfileSortDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderUserProfileSortBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_user_profile_sort,
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

    class ViewHolder(private val binding: HolderUserProfileSortBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Sort) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object UserProfileSortDiffCallback : DiffUtil.ItemCallback<Sort>() {
        override fun areItemsTheSame(oldItem: Sort, newItem: Sort) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Sort, newItem: Sort) =
            oldItem == newItem
    }
}
