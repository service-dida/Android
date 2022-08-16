package com.dida.android.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout

fun startShimmer(shimmer: ShimmerFrameLayout, recyclerView: RecyclerView) {
    shimmer.startShimmer()
    shimmer.visibility = View.VISIBLE
    recyclerView.visibility = View.GONE
}

fun stopShimmer(shimmer: ShimmerFrameLayout, recyclerView: RecyclerView) {
    shimmer.stopShimmer()
    shimmer.visibility = View.GONE
    recyclerView.visibility = View.VISIBLE
}