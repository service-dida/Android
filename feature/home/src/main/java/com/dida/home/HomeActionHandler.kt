package com.dida.home

interface HomeActionHandler {
    fun onHotItemClicked(cardId: Long)
    fun onHotSellerItemClicked(userId: Long)
    fun onSoldOutItemClicked(cardId: Long)
    fun onCollectionItemClicked(userId: Long)
    fun onSoldOutDayClicked(day: Int)
    fun onUserFollowClicked(user: com.dida.domain.model.main.Collection)
    fun onHotSellerMoreClicked()
    fun onSoldOutMoreClicked()
    fun onRecentMoreNftClicked()
    fun onCollectionMoreClicked()
    fun onAddCardClicked()
}
