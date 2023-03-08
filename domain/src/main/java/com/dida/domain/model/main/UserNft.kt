package com.dida.domain.model.main

data class UserNft(
    val cardId : Long,
    val userName : String,
    val cardName : String,
    val imgUrl : String,
    val price : String,
    val liked: Boolean
)