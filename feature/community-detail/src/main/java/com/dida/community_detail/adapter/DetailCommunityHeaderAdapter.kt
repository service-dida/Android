package com.dida.community_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.community_detail.DetailCommunityActionHandler
import com.dida.community_detail.R
import com.dida.community_detail.databinding.HolderDetailCommunityHeaderBinding
import com.dida.community_detail.showReportBalloon
import com.dida.community_detail.showUpdateBalloon
import com.dida.data.DataApplication
import com.dida.domain.main.model.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailCommunityHeaderAdapter(
    private val eventListener: DetailCommunityActionHandler
) : ListAdapter<Post, DetailCommunityHeaderAdapter.ViewHolder>(DetailCommunityHeaderDiffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderDetailCommunityHeaderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_detail_community_header,
            parent,
            false
        )
        viewDataBinding.eventListener = eventListener
        return ViewHolder(viewDataBinding, eventListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(item = it) }
    }

    override fun getItemId(position: Int): Long = getItem(position)?.hashCode()?.toLong() ?: -1

    class ViewHolder(
        private val binding: HolderDetailCommunityHeaderBinding,
        val eventListener: DetailCommunityActionHandler
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post) {
            binding.holder = item
            binding.moreButton.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val userId = DataApplication.dataStorePreferences.getUserId()
                    if (userId == item.memberInfo.memberId) {
                        it.showUpdateBalloon(context = binding.root.context, postId = item.postInfo.postId, eventListener = eventListener)
                    } else {
                        it.showReportBalloon(context = binding.root.context, postId = item.postInfo.postId, eventListener = eventListener)
                    }
                }

            }
            binding.executePendingBindings()
        }
    }

    internal object DetailCommunityHeaderDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}
