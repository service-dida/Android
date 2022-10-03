package com.dida.domain.model.nav.mypage

//TODO : UserCards 데이터 모델 재정의 하기
data class UserNft(
    val cardId : Int,
    val userName : String,
    val cardName : String,
    val imgUrl : String,
    val price : String
){
    fun priceFormat() : String{
        return "$price dida"
    }
}