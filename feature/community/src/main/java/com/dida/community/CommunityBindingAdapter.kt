package com.dida.community

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dida.community.adapter.HotCardAdapter
import com.dida.domain.model.main.HotCard

@BindingAdapter(value = ["hotCardsItem", "eventListener"], requireAll = true)
fun ViewPager2.bindHotCardsItem(contents: List<HotCard>?, eventListener : HotCardActionHandler?) {
    val recyclerView = this
    if (!contents.isNullOrEmpty() && eventListener != null) {
        recyclerView.isVisible = true
        recyclerView.adapter = (recyclerView.adapter as? HotCardAdapter ?: HotCardAdapter(eventListener)).apply {
            submitList(contents)
        }
    } else {
        recyclerView.isGone = true
    }
}
