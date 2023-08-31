package com.dida.notification.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.actionhandler.NotificationActionHandler
import com.dida.domain.model.main.CommonAlarm
import com.dida.notification.list.databinding.HolderNotificationBinding

class NotificationAdapter(
    private val eventListener: NotificationActionHandler
) : PagingDataAdapter<CommonAlarm, NotificationAdapter.ViewHolder>(NotificationItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderNotificationBinding =
            DataBindingUtil.inflate<HolderNotificationBinding?>(
                LayoutInflater.from(parent.context),
                com.dida.notification.list.R.layout.holder_notification,
                parent,
                false
            )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemViewType(position: Int): Int = com.dida.notification.list.R.layout.holder_notification

    class ViewHolder(private val binding: HolderNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommonAlarm) {
            binding.holderModel = item
            binding.executePendingBindings()
        }
    }

    internal object NotificationItemDiffCallback : DiffUtil.ItemCallback<CommonAlarm>() {
        override fun areItemsTheSame(oldItem: CommonAlarm, newItem: CommonAlarm) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CommonAlarm, newItem: CommonAlarm) =
            oldItem == newItem
    }
}
