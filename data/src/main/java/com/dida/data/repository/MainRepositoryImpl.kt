package com.dida.data.repository

import com.dida.data.api.MainAPIService
import com.dida.data.api.handleApi
import com.dida.data.mapper.toDomain
import com.dida.data.model.request.*
import com.dida.data.model.response.PostLikeRequest
import com.dida.data.model.response.PostSellNftRequest
import com.dida.domain.NetworkResult
import com.dida.domain.model.main.Token
import com.dida.domain.model.main.Nickname
import com.dida.domain.model.main.HotCard
import com.dida.domain.model.main.RandomNumber
import com.dida.domain.model.main.DetailNft
import com.dida.domain.model.main.HideCard
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
import com.dida.domain.repository.MainRepository
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val mainAPIService: MainAPIService
): MainRepository {

    override suspend fun checkVersionAPI(): NetworkResult<AppVersion> {
        return handleApi { mainAPIService.checkVersion() }
    }

    override suspend fun loginAPI(idToken: String): NetworkResult<Token> {
        return handleApi { mainAPIService.loginAPIServer(idToken = idToken) }
    }

    override suspend fun nicknameAPI(nickName: String): NetworkResult<Nickname> {
        val request = PostNicknameRequest(nickName)
        return handleApi { mainAPIService.nicknameAPIServer(request) }
    }

    override suspend fun createUserAPI(email: String, nickName: String): NetworkResult<Token> {
        val request = PostCreateUserRequest(email, nickName)
        return handleApi { mainAPIService.createuserAPIServer(request) }
    }

    override suspend fun getUserProfileAPI(): NetworkResult<UserProfile> {
        return handleApi { mainAPIService.getUserProfile().toDomain() }
    }

    override suspend fun refreshTokenAPI(request: String): NetworkResult<Token> {
        return handleApi { mainAPIService.refreshtokenAPIServer(request) }
    }

    override suspend fun getUserCardsAPI(page: Int): NetworkResult<List<UserNft>> {
        return handleApi { mainAPIService.getUserCards(page = page) }
    }

    override suspend fun getSendEmailAPI(): NetworkResult<RandomNumber> {
        return handleApi { mainAPIService.getSendEmail().toDomain() }
    }

    override suspend fun getMainAPI(): NetworkResult<Home> {
        return handleApi { mainAPIService.getMain().toDomain() }
    }

    override suspend fun getSoldOutAPI(term: Int): NetworkResult<List<SoldOut>> {
        return handleApi { mainAPIService.getSoldOut(term).toDomain() }
    }

    override suspend fun mintNFT(payPwd: String,name: String, description: String, image: String): NetworkResult<Long> {
        val request = PostNftMintRequest(payPwd, name,description,image)
        return handleApi { mainAPIService.mintNFT(request) }
    }

    override suspend fun postCreateWalletAPI(password: String, passwordCheck: String, ): NetworkResult<Unit> {
        val request = PostCreateWalletRequest(password, passwordCheck)
        return handleApi { mainAPIService.postCreateWallet(request) }
    }

    override suspend fun getWalletExistsAPI(): NetworkResult<Boolean> {
        return handleApi { mainAPIService.getWalletExists().existed }
    }

    override suspend fun getCheckPasswordAPI(password: String): NetworkResult<Boolean> {
        val request = PostCheckPasswordRequest(password)
        return handleApi { mainAPIService.postCheckPassword(request).toDomain() }
    }

    override suspend fun postChangePasswordAPI(beforePassword: String, afterPassword: String): NetworkResult<Unit> {
        val request = PostPasswordChangeRequest(nowPwd = beforePassword, changePwd = afterPassword)
        return handleApi { mainAPIService.postPasswordChange(request) }
    }

    override suspend fun getTempPasswordAPI(): NetworkResult<Unit> {
        return handleApi { mainAPIService.getTempPassword() }
    }

    override suspend fun updateProfileImageAPI(file : MultipartBody.Part) : NetworkResult<Unit> {
        return handleApi { mainAPIService.updateProfileImage(file)}
    }

    override suspend fun updateProfileDescriptionAPI(description: String) : NetworkResult<Unit> {
        val body = PutUserDescriptionRequest(description = description)
        return handleApi { mainAPIService.updateProfileDescription(body) }
    }

    override suspend fun updateProfileNicknameAPI(nickname: String): NetworkResult<Unit> {
        val body = PutUserNicknameRequest(nickname = nickname)
        return handleApi { mainAPIService.updateProfileNickname(body) }
    }

    override suspend fun postLikeAPI(cardId: Long): NetworkResult<Unit> {
        val request = PostLikeRequest(cardId = cardId)
        return handleApi { mainAPIService.postLike(request) }
    }

    override suspend fun postUserFollowAPI(userId: Long): NetworkResult<Unit> {
        val request = PostUserFollowRequest(userId = userId)
        return handleApi { mainAPIService.postUserFollow(request) }
    }

    override suspend fun getDetailNFT(cardId: Long): NetworkResult<DetailNft> {
        return handleApi { mainAPIService.getDetailNFT(cardId = cardId.toString()).toDomain() }

    }
    override suspend fun putDeviceTokenAPI(deviceToken: String): NetworkResult<Unit> {
        val request = PutDeviceTokenRequest(deviceToken)
        return handleApi { mainAPIService.putDeviceToken(request) }
    }

    override suspend fun postBuyNftAPI(password: String, nftId: Long): NetworkResult<Unit> {
        val request = PostBuyNftRequest(buyPwd = password, marketId = nftId)
        return handleApi { mainAPIService.postBuyNft(request) }
    }

    override suspend fun postSwapKlayToDida(password: String, klay: Double): NetworkResult<Unit> {
        val request = PostSwapKlayToDidaRequest(payPwd = password, klay = klay)
        return handleApi { mainAPIService.postSwapKlayToDida(request) }
    }

    override suspend fun postSwapDidaToKlay(password: String, dida: Double): NetworkResult<Unit> {
        val request = PostSwapDidaToKlayRequest(payPwd = password, dida = dida)
        return handleApi { mainAPIService.postSwapDidaToKlay(request) }
    }

    override suspend fun postSellNftAPI(payPwd: String, cardId: Long, price: Double): NetworkResult<Unit> {
        val request = PostSellNftRequest(payPwd = payPwd, cardId = cardId,price = price)
        return handleApi { mainAPIService.postSellNft(request) }
    }

    override suspend fun getWalletAmountAPI(): NetworkResult<WalletAmount> {
        return handleApi { mainAPIService.getWalletAmount().toDomain() }
    }

    override suspend fun getSwapHistoryAPI(): NetworkResult<List<SwapHistory>> {
        return handleApi { mainAPIService.getSwapHistory().toDomain() }
    }

    override suspend fun getPosts(page: Int): NetworkResult<List<Posts>> {
        return handleApi { mainAPIService.getPosts(page = page).toDomain() }
    }

    override suspend fun getPostPostId(postId: Long): NetworkResult<Post> {
        return handleApi { mainAPIService.getPostPostId(postId = postId).toDomain() }
    }

    override suspend fun getCommentsPostId(postId: Long): NetworkResult<List<Comments>> {
        return handleApi { mainAPIService.getCommentsPostId(postId = postId).toDomain() }
    }

    override suspend fun getCardsPostLike(): NetworkResult<List<CardPost>> {
        return handleApi { mainAPIService.getCardsPostLike().toDomain() }
    }

    override suspend fun getCardsPostMy(): NetworkResult<List<CardPost>> {
        return handleApi { mainAPIService.getCardsPostMy().toDomain() }
    }

    override suspend fun getPostsCardCardId(cardId: Long): NetworkResult<List<Posts>> {
        return handleApi { mainAPIService.getPostsCardCardId(cardId = cardId).toDomain() }
    }

    override suspend fun getHotCards(): NetworkResult<List<HotCard>> {
        return handleApi { mainAPIService.getHotCards().toDomain() }
    }

    override suspend fun postPostCardId(cardId: Long, title: String, content: String): NetworkResult<Unit> {
        val body = PostPostCardIdRequest(title = title, content = content)
        return handleApi { mainAPIService.postPostCardId(cardId = cardId, body = body) }
    }

    override suspend fun patchPostPostId(
        postId: Long,
        title: String,
        content: String
    ): NetworkResult<Unit> {
        val body = PostPostCardIdRequest(title = title, content = content)
        return handleApi { mainAPIService.patchPostPostId(postId = postId, body = body) }
    }

    override suspend fun patchPostPostIdStatus(postId: Long): NetworkResult<Unit> {
        return handleApi { mainAPIService.patchPostPostIdStatus(postId = postId) }
    }

    override suspend fun postComment(postId: Long, content: String): NetworkResult<Unit> {
        val body = PostCommentRequest(postId = postId, content = content)
        return handleApi { mainAPIService.postComment(body = body) }
    }

    override suspend fun patchCommentIdStatus(commentId: Long): NetworkResult<Unit> {
        return handleApi { mainAPIService.patchCommentIdStatus(commentId = commentId) }
    }

    override suspend fun getBuySellList(): NetworkResult<List<TradeHistory>> {
        return handleApi { mainAPIService.getBuySellList().toDomain() }
    }

    override suspend fun getBuyList(): NetworkResult<List<TradeHistory>> {
        return handleApi { mainAPIService.getBuyList().toDomain() }
    }

    override suspend fun getSellList(): NetworkResult<List<TradeHistory>> {
        return handleApi { mainAPIService.getSellList().toDomain() }
    }

    override suspend fun getRecentCard(page: Int): NetworkResult<List<UserNft>> {
        return handleApi { mainAPIService.getRecentCard(page = page).toDomain() }
    }

    override suspend fun getHideList(): NetworkResult<List<HideCard>> {
        return handleApi { mainAPIService.getHideList().toDomain() }
    }

    override suspend fun getHideNft(cardId : Long): NetworkResult<Unit> {
        return handleApi { mainAPIService.getHideNft(cardId = cardId) }
    }

    override suspend fun getHideCancelNft(cardId: Long): NetworkResult<Unit> {
        return handleApi { mainAPIService.getHideCancelNft(cardId = cardId) }
    }

    override suspend fun getUserUserId(userId: Long): NetworkResult<OtherUserProfie> {
        return handleApi { mainAPIService.getUserUserId(userId = userId).toDomain() }
    }

    override suspend fun getUserCardsUserId(userId: Long, page: Int): NetworkResult<List<UserNft>> {
        return handleApi { mainAPIService.getUserCardsUserId(userId = userId, page = page).toDomain() }
    }

    override suspend fun patchDeleteNft(cardId: Long,payPwd: String): NetworkResult<Long> {
        val request = PatchNftRemoveRequest(cardId, payPwd)
        return handleApi { mainAPIService.patchDeleteNft(request) }
    }

    override suspend fun getHotUser(page: Int): NetworkResult<List<HotUser>> {
        return handleApi { mainAPIService.getHotUser(page = page).toDomain() }
    }

    override suspend fun getHotSeller(page: Int): NetworkResult<List<HotSellerMore>> {
        return handleApi { mainAPIService.getHotSeller(page = page).toDomain() }
    }

    override suspend fun postReportUser(userId: Long, content: String): NetworkResult<Unit> {
        val request = PostReportRequest(reportedId = userId, content = content)
        return handleApi { mainAPIService.postReportUser(request) }
    }

    override suspend fun postReportPost(postId: Long, content: String): NetworkResult<Unit> {
        val request = PostReportRequest(reportedId = postId, content = content)
        return handleApi { mainAPIService.postReportPost(request) }
    }

    override suspend fun postReportCard(cardId: Long, content: String): NetworkResult<Unit> {
        val request = PostReportRequest(reportedId = cardId, content = content)
        return handleApi { mainAPIService.postReportCard(request) }
    }
}

