package com.dida.model.data.response

import com.google.gson.annotations.SerializedName

data class GetUserCardsResponse(
    val userCardList: List<UserCardResponse>
)

data class UserCardResponse(
    @SerializedName("cardId") val cardId: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("cardName") val cardName: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("price") val price: String,
    @SerializedName("liked") val liked: Boolean,
)
