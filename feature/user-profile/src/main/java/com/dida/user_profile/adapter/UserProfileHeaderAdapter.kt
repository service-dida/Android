package com.dida.user_profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.domain.main.model.CommonProfile
import com.dida.user_profile.R
import com.dida.user_profile.UserProfileActionHandler
import com.dida.user_profile.databinding.HolderUserProfileHeaderBinding

class UserProfileHeaderAdapter(
    private val eventListener: UserProfileActionHandler
) : ListAdapter<CommonProfile, UserProfileHeaderAdapter.ViewHolder>(UserProfileHeaderDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderUserProfileHeaderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_user_profile_header,
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

    class ViewHolder(private val binding: HolderUserProfileHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommonProfile) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object UserProfileHeaderDiffCallback : DiffUtil.ItemCallback<CommonProfile>() {
        override fun areItemsTheSame(oldItem: CommonProfile, newItem: CommonProfile) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CommonProfile, newItem: CommonProfile) =
            oldItem == newItem
    }
}
