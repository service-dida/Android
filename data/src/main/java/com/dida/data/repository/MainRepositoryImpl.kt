package com.dida.data.repository

import com.dida.data.api.MainAPIService
import com.dida.data.api.handleApi
import com.dida.data.mapper.toDomain
import com.dida.data.model.createwallet.PostCheckPasswordRequest
import com.dida.data.model.createwallet.PostCreateWalletRequest
import com.dida.data.model.device.PutDeviceTokenRequest
import com.dida.data.model.klaytn.NFTMintRequest
import com.dida.data.model.login.CreateUserRequestModel
import com.dida.data.model.main.PostLikeRequest
import com.dida.data.model.main.PostUserFollowRequest
import com.dida.data.model.nickname.PostNicknameRequest
import com.dida.data.model.swap.PostSwapDidaToKlayRequest
import com.dida.data.model.swap.PostSwapKlayToDidaRequest
import com.dida.data.model.tradenft.PostBuyNftRequest
import com.dida.data.model.tradenft.PostSellNftRequest
import com.dida.data.model.userInfo.PostPasswordChangeRequest
import com.dida.data.model.userInfo.PutUserDescriptionRequest
import com.dida.data.model.userInfo.PutUserNicknameRequest
import com.dida.domain.NetworkResult
import com.dida.domain.model.login.LoginResponseModel
import com.dida.domain.model.login.NicknameResponseModel
import com.dida.domain.model.nav.community.HotCard
import com.dida.domain.model.nav.createwallet.RandomNumber
import com.dida.domain.model.nav.detailnft.DetailNFT
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.model.nav.mypage.UserNft
import com.dida.domain.model.nav.mypage.UserProfile
import com.dida.domain.model.nav.post.CardPost
import com.dida.domain.model.nav.post.Comments
import com.dida.domain.model.nav.post.Post
import com.dida.domain.model.nav.post.Posts
import com.dida.domain.model.nav.swap.WalletAmount
import com.dida.domain.model.splash.AppVersionResponse
import com.dida.domain.repository.MainRepository
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val mainAPIService: MainAPIService
): MainRepository {

    override suspend fun checkVersionAPI(): NetworkResult<AppVersionResponse> {
        return handleApi { mainAPIService.checkVersion() }
    }

    override suspend fun loginAPI(idToken: String): NetworkResult<LoginResponseModel> {
        return handleApi { mainAPIService.loginAPIServer(idToken = idToken) }
    }

    override suspend fun nicknameAPI(nickName: String): NetworkResult<NicknameResponseModel> {
        val request = PostNicknameRequest(nickName)
        return handleApi { mainAPIService.nicknameAPIServer(request) }
    }

    override suspend fun createUserAPI(email: String, nickName: String): NetworkResult<LoginResponseModel> {
        val request = CreateUserRequestModel(email, nickName)
        return handleApi { mainAPIService.createuserAPIServer(request) }
    }

    override suspend fun getUserProfileAPI(): NetworkResult<UserProfile> {
        return handleApi { mainAPIService.getUserProfile().toDomain() }
    }

    override suspend fun refreshTokenAPI(request: String): NetworkResult<LoginResponseModel> {
        return handleApi { mainAPIService.refreshtokenAPIServer(request) }
    }

    override suspend fun getUserCardsAPI(): NetworkResult<List<UserNft>> {
        return handleApi { mainAPIService.getUserCards() }
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

    override suspend fun mintNFT(payPwd: String,name: String, description: String, image: String): NetworkResult<Unit> {
        val request = NFTMintRequest(payPwd, name,description,image)
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

    override suspend fun getDetailNFT(cardId: Long): NetworkResult<DetailNFT> {
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

    override suspend fun getPosts(page: Int): NetworkResult<List<Posts>> {
        return handleApi { mainAPIService.getPosts(page = page).toDomain() }
    }

    override suspend fun getPostPostId(postId: Int): NetworkResult<Post> {
        return handleApi { mainAPIService.getPostPostId(postId = postId).toDomain() }
    }

    override suspend fun getCommentsPostId(postId: Int): NetworkResult<List<Comments>> {
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
}
