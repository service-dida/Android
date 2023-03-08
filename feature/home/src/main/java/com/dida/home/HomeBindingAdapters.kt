package com.dida.home

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.adapter.RecentNftAdapter
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.model.main.Home
import com.dida.domain.model.main.SoldOut
import com.dida.home.adapter.*

@BindingAdapter("hotsItem")
fun RecyclerView.bindHotsItems(uiState: UiState<Home>) {
    val boundAdapter = this.adapter
    if (boundAdapter is HotsAdapter) {
        boundAdapter.submitList(uiState.successOrNull()?.getHotItems)
    }
}

@BindingAdapter("hotSellerItem")
fun RecyclerView.bindHotSellerItem(uiState: UiState<Home>) {
    val boundAdapter = this.adapter
    if (boundAdapter is HotSellerAdapter) {
        boundAdapter.submitList(uiState.successOrNull()?.getHotSellers)
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
