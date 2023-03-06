package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class GetCardHideListResponse(
    val cardHideListItems: List<CardHideListItemResponse>
)

data class CardHideListItemResponse(
    @SerializedName("cardId") val cardId: Long,
    @SerializedName("cardUrl") val cardUrl: Int,
    @SerializedName("cardTitle") val cardTitle: Int
)
