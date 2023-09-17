package com.dida.home

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.main.model.HotItem
import com.dida.domain.main.model.Main
import com.dida.domain.main.model.SoldOut
import com.dida.home.adapter.*

@BindingAdapter(value = ["hotsItem", "eventListener"], requireAll = true)
fun ViewPager2.bindHotsItems(banners: List<HotItem>?, eventListener : HomeActionHandler?) {
    val recyclerView = this
    if (!banners.isNullOrEmpty() && eventListener != null) {
        recyclerView.isVisible = true
        recyclerView.adapter = (recyclerView.adapter as? HotsAdapter ?: HotsAdapter(eventListener)).apply {
            submitList(banners)
        }
    } else {
        recyclerView.isGone = true
    }
}

@BindingAdapter("recentNftItem")
fun RecyclerView.bindRecentNftItem(uiState: UiState<Main>) {
    val boundAdapter = this.adapter
    if (boundAdapter is RecentNftAdapter) {
        boundAdapter.submitList(uiState.successOrNull()?.getRecentNfts)
    }
}

@BindingAdapter("soldoutItem")
fun RecyclerView.bindSoldoutItem(uiState: UiState<List<SoldOut>>) {
    val boundAdapter = this.adapter
    if (boundAdapter is SoldOutAdapter) {
        boundAdapter.submitList(uiState.successOrNull())
    }
}
