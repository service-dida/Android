package com.dida.data.mapper

import androidx.lifecycle.Transformations.map
import com.dida.data.model.createwallet.SendEmailResponse
import com.dida.data.model.main.*
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.home.*
import com.dida.domain.model.nav.home.Collection
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.google.gson.annotations.SerializedName
import retrofit2.Response


fun mapperMainResponseToMain(items: GetMainResponse): Home {
    return Home(
        getHotItems = mapperHotItemResponseToHot(items.getHotItems),
        getHotSellers = mapperHotSellerResponseToHotSeller(items.getHotSellers),
        getRecentCards = mapperRecentCardResponseToRecentNft(items.getRecentCards),
        getHotUsers = mapperHotUserResponseToCollection(items.getHotUsers)
    )
}

fun mapperHotItemResponseToHot(items: List<HotItemResponse>): List<Hots> {
    return items.toList().map {
        Hots(
            cardId = it.cardId,
            nftImg = it.imgUrl,
            nftName = it.name,
            heartCount = it.count,
            price = it.price
        )
    }
}

fun mapperHotSellerResponseToHotSeller(items: List<HotSellerResponse>): List<HotSeller> {
    return items.toList().map {
        HotSeller(
            userId = it.userId,
            sellerBackground = it.imgUrl,
            sellerProfile = it.profileUrl,
            sellerName = it.name
        )
    }
}

fun mapperRecentCardResponseToRecentNft(items: List<RecentCardResponse>): List<UserCardsResponseModel> {
    return items.toList().map {
        UserCardsResponseModel(
            cardId = it.cardId,
            userName = it.userName,
            imgUrl = it.imgUrl,
            cardName = it.cardName,
            price = it.price
        )
    }
}

fun mapperHotUserResponseToCollection(items: List<HotUserResponse>): List<Collection> {
    return items.toList().map {
        Collection(
            userId = it.userId,
            userImg = it.profileUrl,
            userName = it.name,
            userDetail = it.count+" 작품",
            follow = it.followed
        )
    }
}

fun mapperSoldOutResponseToSoldOut(items: List<GetSoldOutResponse>): List<SoldOut> {
    return items.toList().map {
        SoldOut(
            nftId = it.nftId,
            nftImg = it.imgUrl,
            nftName = it.name,
            userImg = it.profileUrl,
            userName = it.userName,
            price = it.price
        )
    }
}

fun mapperSendEmailResponseToRandomNum(items: SendEmailResponse): RandomNumber {
    return RandomNumber(
        random = items.random
    )
}