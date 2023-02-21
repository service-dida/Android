package com.dida.buy.nft

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dida.common.util.UiState
import com.dida.common.util.successOrNull
import com.dida.domain.model.nav.detailnft.DetailNFT
import kotlinx.coroutines.flow.StateFlow


@BindingAdapter("sellerText")
fun TextView.bindSellerName(detailNFT: StateFlow<DetailNFT>) {
    this.text = "${detailNFT.value.nickname} 판매자"
}

@BindingAdapter("buyerText")
fun TextView.bindBuyerName(detailNFT: StateFlow<DetailNFT>) {
    this.text = " 구매자"
}

@BindingAdapter("priceText")
fun TextView.bindPriceName(detailNFT: StateFlow<DetailNFT>) {
    this.text = "${detailNFT.value.price} dida"
}
