package com.dida.common.util

import android.content.Context
import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView

// RecyclerView - Adapter

val RecyclerView.ViewHolder.context: Context
    get() = itemView.context

val RecyclerView.ViewHolder.resources: Resources
    get() = itemView.resources

fun invalidViewTypeError(): Nothing = error("invalid view type")

fun invalidViewItemError(): Nothing = error("invalid view item")

fun RecyclerView.scrollBy(width: Int) {
    this.postDelayed({
        this.scrollBy(width, 0)
    }, 0)
}
