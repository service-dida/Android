package com.dida.hot_user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.hot_user.R
import com.dida.hot_user.databinding.HolderCardBinding

class UserCardAdapter : ListAdapter<String, UserCardAdapter.ViewHolder>(CardDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_card,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = R.layout.holder_card

    class ViewHolder(private val binding: HolderCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.imageUrl = item
            binding.executePendingBindings()
        }
    }

    internal object CardDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }
}
