package com.dida.home

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.home.adapter.RecentNftAdapter
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.model.main.Home
import com.dida.domain.model.main.Hots
import com.dida.domain.model.main.SoldOut
import com.dida.home.adapter.*

@BindingAdapter(value = ["hotsItem", "eventListener"], requireAll = true)
fun RecyclerView.bindHotsItems(banners: List<Hots>?, eventListener : HomeActionHandler?) {
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

@BindingAdapter("hotSellerItem")
fun RecyclerView.bindHotSellerItem(uiState: UiState<Home>) {
    val boundAdapter = this.adapter
    if (boundAdapter is ConcatAdapter) {
        (boundAdapter.adapters.get(0) as HotSellerAdapter).submitList(uiState.successOrNull()?.getHotSellers)
    }
}

@BindingAdapter("recentNftItem")
fun RecyclerView.bindRecentNftItem(uiState: UiState<Home>) {
    val boundAdapter = this.adapter
    if (boundAdapter is RecentNftAdapter) {
        boundAdapter.submitList(uiState.successOrNull()?.getRecentCards)
    }
}

@BindingAdapter("collectionsItem")
fun RecyclerView.bindCollectionItem(uiState: UiState<Home>) {
    val boundAdapter = this.adapter
    if (boundAdapter is CollectionAdapter) {
        boundAdapter.submitList(uiState.successOrNull()?.getHotUsers)
    }
}

@BindingAdapter("soldoutItem")
fun RecyclerView.bindSoldoutItem(uiState: UiState<List<SoldOut>>) {
    val boundAdapter = this.adapter
    if (boundAdapter is SoldOutAdapter) {
        boundAdapter.submitList(uiState.successOrNull())
    }
}
