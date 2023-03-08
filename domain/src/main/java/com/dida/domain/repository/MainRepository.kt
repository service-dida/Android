package com.dida.domain.repository

import com.dida.domain.NetworkResult
import com.dida.domain.model.main.Token
import com.dida.domain.model.main.Nickname
import com.dida.domain.model.main.HotCard
import com.dida.domain.model.main.RandomNumber
import com.dida.domain.model.main.DetailNft
import com.dida.domain.model.nav.hide.HideCard
import com.dida.domain.model.main.Home
import com.dida.domain.model.main.HotSellerMore
import com.dida.domain.model.main.HotUser
import com.dida.domain.model.main.SoldOut
import com.dida.domain.model.main.TradeHistory
import com.dida.domain.model.main.OtherUserProfie
import com.dida.domain.model.main.UserNft
import com.dida.domain.model.main.UserProfile
import com.dida.domain.model.main.CardPost
import com.dida.domain.model.main.Comments
import com.dida.domain.model.main.Post
import com.dida.domain.model.main.Posts
import com.dida.domain.model.main.WalletAmount
import com.dida.domain.model.main.SwapHistory
import com.dida.domain.model.main.AppVersion
import okhttp3.MultipartBody

interface  MainRepository {

    suspend fun checkVersionAPI(): NetworkResult<AppVersion>

    suspend fun loginAPI(idToken : String): NetworkResult<Token>

    suspend fun nicknameAPI(nickName: String): NetworkResult<Nickname>

    suspend fun createUserAPI(email: String, nickName: String): NetworkResult<Token>

    suspend fun getUserProfileAPI() : NetworkResult<UserProfile>

    suspend fun refreshTokenAPI(request: String): NetworkResult<Token>

    suspend fun getUserCardsAPI() : NetworkResult<List<UserNft>>

    suspend fun getSendEmailAPI() : NetworkResult<RandomNumber>

    suspend fun postCreateWalletAPI(password: String, passwordCheck: String) : NetworkResult<Unit>

    suspend fun getWalletExistsAPI() : NetworkResult<Boolean>

    suspend fun getCheckPasswordAPI(password: String) : NetworkResult<Boolean>

    suspend fun postChangePasswordAPI(beforePassword: String, afterPassword: String) : NetworkResult<Unit>

    suspend fun getTempPasswordAPI() : NetworkResult<Unit>

    suspend fun getMainAPI() : NetworkResult<Home>

    suspend fun getSoldOutAPI(term: Int) : NetworkResult<List<SoldOut>>

    suspend fun mintNFT(payPwd : String, name : String, description : String, image : String) : NetworkResult<Long>

    suspend fun updateProfileImageAPI(file : MultipartBody.Part) : NetworkResult<Unit>

    suspend fun updateProfileDescriptionAPI(description: String) : NetworkResult<Unit>

    suspend fun updateProfileNicknameAPI(nickname: String) : NetworkResult<Unit>

    suspend fun postLikeAPI(cardId: Long) : NetworkResult<Unit>

    suspend fun postUserFollowAPI(userId: Long) : NetworkResult<Unit>

    suspend fun getDetailNFT(cardId: Long) : NetworkResult<DetailNft>

    suspend fun putDeviceTokenAPI(deviceToken: String) : NetworkResult<Unit>

    suspend fun postBuyNftAPI(password: String, nftId: Long) : NetworkResult<Unit>

    suspend fun postSwapKlayToDida(password: String, klay: Double) : NetworkResult<Unit>

    suspend fun postSwapDidaToKlay(password: String, dida: Double) : NetworkResult<Unit>

    suspend fun postSellNftAPI(payPwd : String, cardId : Long, price : Double) : NetworkResult<Unit>

    suspend fun getWalletAmountAPI() : NetworkResult<WalletAmount>

    suspend fun getSwapHistoryAPI() : NetworkResult<List<SwapHistory>>

    suspend fun getPosts(page: Int) : NetworkResult<List<Posts>>

    suspend fun getPostPostId(postId: Long) : NetworkResult<Post>

    suspend fun getCommentsPostId(postId: Long) : NetworkResult<List<Comments>>

    suspend fun getCardsPostLike() : NetworkResult<List<CardPost>>

    suspend fun getCardsPostMy() : NetworkResult<List<CardPost>>

    suspend fun getPostsCardCardId(cardId: Long) : NetworkResult<List<Posts>>

    suspend fun getHotCards() : NetworkResult<List<HotCard>>

    suspend fun postPostCardId(cardId: Long, title: String, content: String) : NetworkResult<Unit>

    suspend fun patchPostPostId(postId: Long, title: String, content: String) : NetworkResult<Unit>

    suspend fun patchPostPostIdStatus(postId: Long) : NetworkResult<Unit>

    suspend fun postComment(postId: Long, content: String): NetworkResult<Unit>

    suspend fun patchCommentIdStatus(commentId: Long) : NetworkResult<Unit>

    suspend fun getBuySellList() : NetworkResult<List<TradeHistory>>

    suspend fun getBuyList() : NetworkResult<List<TradeHistory>>

    suspend fun getSellList() : NetworkResult<List<TradeHistory>>

    suspend fun getRecentCard(page: Int) : NetworkResult<List<UserNft>>

    suspend fun getHideList() : NetworkResult<List<HideCard>>

    suspend fun getHideNft(cardId : Long) : NetworkResult<Unit>

    suspend fun getHideCancelNft(cardId : Long) : NetworkResult<Unit>

    suspend fun getUserUserId(userId: Long) : NetworkResult<OtherUserProfie>

    suspend fun getUserCardsUserId(userId: Long) : NetworkResult<List<UserNft>>

    suspend fun patchDeleteNft(cardId: Long,payPwd : String) : NetworkResult<Long>

    suspend fun getHotUser(page: Int): NetworkResult<List<HotUser>>

    suspend fun getHotSeller(page: Int): NetworkResult<List<HotSellerMore>>
}
