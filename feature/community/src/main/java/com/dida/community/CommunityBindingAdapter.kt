package com.dida.community

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dida.community.adapter.HotCardAdapter
import com.dida.domain.main.model.HotPost

@BindingAdapter(value = ["hotCardsItem", "eventListener"], requireAll = true)
fun ViewPager2.bindHotCardsItem(contents: List<HotPost>?, eventListener : HotCardActionHandler?) {
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
