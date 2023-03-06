package com.dida.home

interface HomeActionHandler {
    fun onHotItemClicked(cardId: Long)
    fun onHotSellerItemClicked(userId: Long)
    fun onSoldOutItemClicked(cardId: Long)
    fun onCollectionItemClicked(userId: Long)
    fun onSoldOutDayClicked(day: Int)
    fun onUserFollowClicked(userId: Long)
    fun onHotSellerMoreClicked()
    fun onSoldOutMoreClicked()
    fun onRecentMoreNftClicked()
    fun onCollectionMoreClicked()
}
