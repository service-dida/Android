package com.dida.home

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dida.home.adapter.RecentNftAdapter
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.model.main.Home
import com.dida.domain.model.main.Hots
import com.dida.domain.model.main.SoldOut
import com.dida.home.adapter.*

@BindingAdapter(value = ["hotsItem", "eventListener"], requireAll = true)
fun ViewPager2.bindHotsItems(banners: List<Hots>?, eventListener : HomeActionHandler?) {
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
fun RecyclerView.bindRecentNftItem(uiState: UiState<Home>) {
    val boundAdapter = this.adapter
    if (boundAdapter is RecentNftAdapter) {
        boundAdapter.submitList(uiState.successOrNull()?.getRecentCards)
    }
}

@BindingAdapter("soldoutItem")
fun RecyclerView.bindSoldoutItem(uiState: UiState<List<SoldOut>>) {
    val boundAdapter = this.adapter
    if (boundAdapter is SoldOutAdapter) {
        boundAdapter.submitList(uiState.successOrNull())
    }
}
