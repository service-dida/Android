package com.dida.home

import com.dida.domain.main.model.HotMember

interface HomeActionHandler {
    fun onHotItemClicked(cardId: Long)
    fun onHotSellerItemClicked(userId: Long)
    fun onSoldOutItemClicked(cardId: Long)
    fun onCollectionItemClicked(userId: Long)
    fun onSoldOutDayClicked(day: Int)
    fun onUserFollowClicked(user: HotMember)
    fun onHotSellerMoreClicked()
    fun onSoldOutMoreClicked()
    fun onRecentMoreNftClicked()
    fun onCollectionMoreClicked()
    fun onAddCardClicked()
}
