package com.dida.android.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.android.presentation.adapter.home.*
import com.dida.android.presentation.base.UiState
import com.dida.android.presentation.base.successOrNull
import com.dida.domain.model.nav.home.*
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@BindingAdapter("startLoading")
fun ShimmerFrameLayout.startLoading(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.VISIBLE else View.GONE
}

@BindingAdapter("endLoading")
fun View.endLoading(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.GONE else View.VISIBLE
}
