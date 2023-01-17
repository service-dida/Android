package com.dida.model.mapper

import com.dida.model.data.response.*
import com.dida.model.domain.*

fun GetAppVersionResponse.toDomain(): AppVersion {
    val response = this
    return AppVersion(version = response.version)
}

fun GetUserResponse.toDomain(): User {
    val response = this
    return User(
        userId = response.userId,
        nickname = response.nickname,
        profileUrl = response.profileUrl,
        description = response.description,
        getWallet = response.getWallet,
        cardCnt = response.cardCnt,
        followerCnt = response.followerCnt,
        followingCnt = response.followingCnt
    )
}

fun List<UserCardResponse>.toDomain(): List<UserCard> {
    return map {
        val response = it
        UserCard(
            cardId = response.cardId,
            userName = response.userName,
            cardName = response.cardName,
            imgUrl = response.imgUrl,
            price = response.price,
            liked = response.liked
        )
    }
}

fun GetAuthMailResponse.toDomain(): AuthMail {
    val response = this
    return AuthMail(random = response.random)
}

fun GetMainResponse.toDomain() : Main {
    return Main(
        getHotItems = getHotItems.toDomain(),
        getHotSellers = getHotSellers.toDomain(),
        getRecentCards = getRecentCards.toDomain(),
        getHotUsers = getHotUsers.toDomain()
    )
}

@JvmName("toDomainHotItemResponse")
fun List<HotItemResponse>.toDomain(): List<HotItem> {
    return map {
        val response = it
        HotItem(
            cardId = response.cardId,
            nftImg = response.imgUrl,
            nftName = response.name,
            heartCount = response.count,
            price = response.price
        )
    }
}

@JvmName("toDomainHotSellerResponse")
fun List<HotSellerResponse>.toDomain(): List<HotSeller> {
    return map {
        val response = it
        HotSeller(
            userId = response.userId,
            sellerBackground = response.imgUrl,
            sellerProfile = response.profileUrl,
            sellerName = response.name
        )
    }
}

@JvmName("toDomainRecentCardResponse")
fun List<RecentCardResponse>.toDomain(): List<RecentCard> {
    return map {
        val response = it
        RecentCard(
            cardId = response.cardId,
            userName = response.userName,
            imgUrl = response.imgUrl,
            cardName = response.cardName,
            price = response.price,
            liked = response.liked
        )
    }
}

@JvmName("toDomainHotUserResponse")
fun List<HotUserResponse>.toDomain(): List<HotUser> {
    return map {
        val response = it
        HotUser(
            userId = response.userId,
            userImg = response.profileUrl,
            userName = response.name,
            userDetail = response.count+" 작품",
            follow = response.followed
        )
    }
}

@JvmName("toDomainMainTermItemResponse")
fun List<MainTermItemResponse>.toDomain(): List<MainTermItem> {
    return map {
        val response = it
        MainTermItem(
            nftId = response.nftId,
            nftImg = response.nftImg,
            nftName = response.nftName,
            userImg = response.userImg,
            userName = response.userName,
            price = response.price
        )
    }
}

fun GetCardResponse.toDomain() : CardResponse {
    val response = this
    return CardResponse(
        cardId = response.cardId,
        title = response.title,
        description = response.description,
        imgUrl = response.imgUrl,
        userId = response.userId,
        nickname = response.nickname,
        profileUrl = response.profileUrl,
        id = response.id,
        contracts = response.contracts,
        liked = response.liked,
        type = response.type,
        price = response.price
    )
}

@JvmName("toDomainCardPostLikeItemResponse")
fun List<CardPostLikeItemResponse>.toDomain(): List<CardsPostLike> {
    return map {
        val response = it
        CardsPostLike(
            cardId = response.cardId,
            cardImgUrl = response.cardImgUrl,
            cardName = response.cardName,
            userImgUrl = response.userImgUrl,
            userName = response.userName
        )
    }
}

@JvmName("toDomainPostsItemResponse")
fun List<PostsItemResponse>.toDomain(): List<PostItem> {
    return map {
        val response = it
        PostItem(
            postId = response.postId,
            cardId = response.cardId,
            userName = response.userName,
            userImgUrl = response.userImgUrl,
            title = response.title,
            content = response.content,
            cardName = response.cardName,
            cardImgUrl = response.cardImgUrl,
            price = response.price,
            cardOwnerImgUrl = response.cardOwnerImgUrl
        )
    }
}

fun GetPostPostIdResponse.toDomain() : Post {
    val response = this
    return Post(
        postId = response.postId,
        cardId = response.cardId,
        userName = response.userName,
        userImgUrl = response.userImgUrl,
        title = response.title,
        content = response.content,
        cardName = response.cardName,
        cardImgUrl = response.cardImgUrl,
        price = response.price,
        cardOwnerImgUrl = response.cardOwnerImgUrl,
        type = response.type
    )
}

@JvmName("toDomainCommentItemResponse")
fun List<CommentItemResponse>.toDomain(): List<Comment> {
    return map {
        val response = it
        Comment(
            commentId = response.commentId,
            postId = response.postId,
            content = response.content,
            userName = response.userName,
            userImgUrl = response.userImgUrl,
            type = response.type
        )
    }
}

@JvmName("toDomainCardHideListItemResponse")
fun List<CardHideListItemResponse>.toDomain(): List<CardHideListItem> {
    return map {
        val response = it
        CardHideListItem(
            cardId = response.cardId,
            cardUrl = response.cardUrl,
            cardTitle = response.cardTitle
        )
    }
}
