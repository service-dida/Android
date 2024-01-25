package com.dida.common.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.util.UiState
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("startLoading")
fun ShimmerFrameLayout.startLoading(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.VISIBLE else View.GONE
    this.startShimmer()
}

@BindingAdapter("rv_endLoading")
fun RecyclerView.endLoading(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.GONE else View.VISIBLE
}

@BindingAdapter("constraint_endLoading")
fun ConstraintLayout.endLoading(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.GONE else View.VISIBLE
}

@BindingAdapter("linear_endLoading")
fun LinearLayout.endLoading(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.GONE else View.VISIBLE
}

@BindingAdapter("tv_endLoading")
fun TextView.endLoading(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.GONE else View.VISIBLE
}

@BindingAdapter("iv_endLoading")
fun ImageView.endLoading(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.GONE else View.VISIBLE
}

@BindingAdapter("nsv_endLoading")
fun NestedScrollView.endLoading(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.GONE else View.VISIBLE
}

@BindingAdapter("fab_endLoading")
fun FloatingActionButton.endLoading(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.GONE else View.VISIBLE
}

@BindingAdapter("ctl_endLoading")
fun CoordinatorLayout.endLoading(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.GONE else View.VISIBLE
}
