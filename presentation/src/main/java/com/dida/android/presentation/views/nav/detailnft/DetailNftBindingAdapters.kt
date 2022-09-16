package com.dida.android.presentation.views.nav.detailnft

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.presentation.adapter.detailnft.CommunityAdapter
import com.dida.android.presentation.adapter.home.*
import com.dida.android.presentation.base.UiState
import com.dida.android.presentation.base.successOrNull
import com.dida.domain.model.nav.detailnft.Community
import com.dida.domain.model.nav.home.*

@BindingAdapter("communityAdapter")
fun RecyclerView.bindCommnuityItem(uiState: UiState<List<Community>>) {
    val boundAdapter = this.adapter
    if (boundAdapter is CommunityAdapter) {
        boundAdapter.submitList(uiState.successOrNull())
    }
}