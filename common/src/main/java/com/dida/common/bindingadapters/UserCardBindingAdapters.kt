package com.dida.common.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.actionhandler.NftActionHandler
import com.dida.common.adapter.UserCardAdapter
import com.dida.domain.main.model.CommonProfileNft

@BindingAdapter(value = ["userCardItems", "eventListener"], requireAll = false)
fun bindUserCardItems(
    recyclerView: RecyclerView,
    items: List<CommonProfileNft>?,
    eventListener: NftActionHandler?
) {
    if (!items.isNullOrEmpty() && eventListener != null) {
        recyclerView.adapter = (recyclerView.adapter as? UserCardAdapter ?: UserCardAdapter(
            eventListener
        )).apply { submitList(items) }
    }
}
