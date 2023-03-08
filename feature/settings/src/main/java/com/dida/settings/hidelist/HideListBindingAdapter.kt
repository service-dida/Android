package com.dida.settings.hidelist

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.model.nav.hide.HideCard

@BindingAdapter("hideListItem")
fun RecyclerView.bindHideListItem(uiState: UiState<List<HideCard>>) {
    val boundAdapter = this.adapter
    if (boundAdapter is HideListAdapter) {
        boundAdapter.submitList(uiState.successOrNull())
    }
}