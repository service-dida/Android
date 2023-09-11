package com.dida.data.main

import com.dida.data.api.handleApi
import com.dida.data.model.dex.toDomain
import com.dida.data.model.login.PatchMemberDeviceRequest
import com.dida.data.model.login.PostLoginRequest
import com.dida.data.model.login.PostNicknameRequest
import com.dida.data.model.login.PostUserRequest
import com.dida.data.model.login.toDomain
import com.dida.data.model.main.toDomain
import com.dida.data.model.market.toDomain
import com.dida.data.model.profile.PatchProfileDescriptionRequest
import com.dida.data.model.profile.PatchProfileNicknameRequest
import com.dida.data.model.profile.toDomain
import com.dida.data.model.sns.PatchCommonPostRequest
import com.dida.data.model.sns.PostCommonCommentsRequest
import com.dida.data.model.sns.PostCommonPostRequest
import com.dida.data.model.sns.toDomain
import com.dida.domain.NetworkResult
import com.dida.domain.main.MainRepository
import com.dida.domain.main.model.CommonProfile
import com.dida.domain.main.model.CommonProfileNft
import com.dida.domain.Contents
import com.dida.domain.main.model.CommonFollow
import com.dida.domain.main.model.LoginToken
import com.dida.domain.main.model.MemberProfile
import com.dida.domain.main.model.MemberWallet
import com.dida.domain.main.model.Nft
import com.dida.domain.main.model.Post
import com.dida.domain.main.model.RecentNft
import com.dida.domain.main.model.Swap
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val didaApi: DidaApi
): MainRepository {

    override suspend fun login(idToken: String): NetworkResult<LoginToken> {
        val body = PostLoginRequest(idToken = idToken)
        return handleApi { didaApi.login(body).toDomain() }
    }

    override suspend fun postUser(email: String, nickname: String): NetworkResult<LoginToken> {
        val body = PostUserRequest(email = email, nickname = nickname)
        return handleApi { didaApi.postUser(body).toDomain() }
    }

    override suspend fun checkNickname(nickname: String): NetworkResult<Boolean> {
        val body = PostNicknameRequest(nickname = nickname)
        return handleApi { didaApi.postNickname(body).used }
    }

    override suspend fun refreshToken(refreshToken: String): NetworkResult<LoginToken> {
        return handleApi { didaApi.patchRefreshToken(request = refreshToken).toDomain() }
    }

    override suspend fun emailAuth(): NetworkResult<String> {
        return handleApi { didaApi.getEmailAuth().random }
    }

    override suspend fun checkWallet(): NetworkResult<Boolean> {
        return handleApi { didaApi.getCommonWallet().existed }
    }

    override suspend fun patchDeviceToken(deviceToken: String): NetworkResult<Unit> {
        val body = PatchMemberDeviceRequest(deviceToken = deviceToken)
        return handleApi { didaApi.patchMemberDevice(body) }
    }

    override suspend fun deleteUser(): NetworkResult<Unit> {
        return handleApi { didaApi.deleteUser() }
    }

    override suspend fun commonProfile(): NetworkResult<CommonProfile> {
        return handleApi { didaApi.getCommonProfile().toDomain() }
    }

    override suspend fun commonProfileNft(
        page: Int,
        size: Int,
        sort: String
    ): NetworkResult<Contents<CommonProfileNft>> {
        return handleApi { didaApi.getCommonProfileNft(page, size, sort).toDomain() }
    }

    override suspend fun memberProfile(memberId: Long): NetworkResult<MemberProfile> {
        return handleApi { didaApi.getMemberProfile(memberId = memberId).toDomain() }
    }

    override suspend fun memberProfileNFt(
        memberId: Long,
        page: Int,
        size: Int,
        sort: String
    ): NetworkResult<Contents<CommonProfileNft>> {
        return handleApi { didaApi.getMemberProfileNft(memberId, page, size, sort).toDomain() }
    }

    override suspend fun memberFollow(memberId: Long): NetworkResult<Unit> {
        return handleApi { didaApi.patchMemberFollow(memberId) }
    }

    override suspend fun commonFollow(page: Int, size: Int): NetworkResult<Contents<CommonFollow>> {
        return handleApi { didaApi.getCommonFollow(page, size).toDomain() }
    }

    override suspend fun commonFollowing(page: Int, size: Int): NetworkResult<Contents<CommonFollow>> {
        return handleApi { didaApi.getCommonFollowing(page, size).toDomain() }
    }

    override suspend fun memberWallet(): NetworkResult<MemberWallet> {
        return handleApi { didaApi.getMemberWallet().toDomain() }
    }

    override suspend fun patchProfileImage(file: MultipartBody.Part): NetworkResult<Unit> {
        return handleApi { didaApi.patchProfileImage(file) }
    }

    override suspend fun patchProfileDescription(description: String): NetworkResult<Unit> {
        val body = PatchProfileDescriptionRequest(description)
        return handleApi { didaApi.patchProfileDescription(body) }
    }

    override suspend fun patchProfileNickname(nickname: String): NetworkResult<Unit> {
        val body = PatchProfileNicknameRequest(nickname)
        return handleApi { didaApi.patchProfileNickname(body) }
    }

    override suspend fun patchTempMemberPassword(): NetworkResult<Unit> {
        return handleApi { didaApi.patchTempMemberPassword() }
    }

    override suspend fun deleteNft(nftId: Long): NetworkResult<Unit> {
        return handleApi { didaApi.deleteNft(nftId) }
    }

    override suspend fun memberSwap(page: Int, size: Int): NetworkResult<Contents<Swap>> {
        return handleApi { didaApi.getMemberSwap(page, size).toDomain() }
    }

    override suspend fun nftDetail(nftId: Long): NetworkResult<Nft> {
        return handleApi { didaApi.getNftDetail(nftId).toDomain() }
    }

    override suspend fun recentNfts(page: Int, size: Int): NetworkResult<Contents<RecentNft>> {
        return handleApi { didaApi.getRecentNfts(page, size).toDomain() }
    }

    override suspend fun writePost(nftId: Long, title: String, content: String): NetworkResult<Unit> {
        val body = PostCommonPostRequest(nftId, title, content)
        return handleApi { didaApi.writePost(body) }
    }

    override suspend fun patchPost(postId: Long, title: String, content: String): NetworkResult<Unit> {
        val body = PatchCommonPostRequest(postId, title, content)
        return handleApi { didaApi.patchPost(body) }
    }

    override suspend fun deletePost(postId: Long): NetworkResult<Unit> {
        return handleApi { didaApi.deletePost(postId) }
    }

    override suspend fun posts(page: Int, size: Int): NetworkResult<Contents<Post>> {
        return handleApi { didaApi.getPosts(page, size).toDomain() }
    }

    override suspend fun postsFromNft(nftId: Long, page: Int, size: Int): NetworkResult<Contents<Post>> {
        return handleApi { didaApi.getPostsFromNft(nftId, page, size).toDomain() }
    }

    override suspend fun postDetail(postId: Long): NetworkResult<Post> {
        return handleApi { didaApi.getPostDetail(postId).toDomain() }
    }

    override suspend fun writePostComments(postId: Long, content: String): NetworkResult<Unit> {
        val body = PostCommonCommentsRequest(postId, content)
        return handleApi { didaApi.writePostComments(body) }
    }
}

