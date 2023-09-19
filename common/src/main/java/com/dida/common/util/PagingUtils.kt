package com.dida.common.util

import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.addOnPagingListener(
    arrivedTop: () -> Unit,
    arrivedBottom: () -> Unit
) {
    val recyclerView = this
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (!recyclerView.canScrollVertically(-1)) {
                arrivedTop()
            } else if (!recyclerView.canScrollVertically(1)) {
                arrivedBottom()
            }
        }
    })
}

const val INIT_PAGE = 0
const val PAGE_SIZE = 20
const val UPDATED_DESC = "updated_desc"
const val UPDATED_ASC = "updated_asc"

