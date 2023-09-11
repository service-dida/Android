package com.dida.domain.main

import com.dida.domain.Contents
import com.dida.domain.NetworkResult
import com.dida.domain.main.model.Comment
import com.dida.domain.main.model.CommonProfile
import com.dida.domain.main.model.CommonProfileNft
import com.dida.domain.main.model.CommonFollow
import com.dida.domain.main.model.HotPost
import com.dida.domain.main.model.LoginToken
import com.dida.domain.main.model.MemberProfile
import com.dida.domain.main.model.MemberWallet
import com.dida.domain.main.model.Nft
import com.dida.domain.main.model.OwnNft
import com.dida.domain.main.model.Post
import com.dida.domain.main.model.RecentNft
import com.dida.domain.main.model.Swap
import okhttp3.MultipartBody

interface MainRepository {

    suspend fun login(idToken: String): NetworkResult<LoginToken>
    suspend fun postUser(email: String, nickname: String): NetworkResult<LoginToken>
    suspend fun checkNickname(nickname: String): NetworkResult<Boolean>
    suspend fun refreshToken(refreshToken: String): NetworkResult<LoginToken>
    suspend fun emailAuth(): NetworkResult<String>
    suspend fun checkWallet(): NetworkResult<Boolean>
    suspend fun patchDeviceToken(deviceToken: String): NetworkResult<Unit>
    suspend fun deleteUser(): NetworkResult<Unit>
    suspend fun commonProfile(): NetworkResult<CommonProfile>
    suspend fun commonProfileNft(page: Int, size: Int, sort: String): NetworkResult<Contents<CommonProfileNft>>
    suspend fun memberProfile(memberId: Long): NetworkResult<MemberProfile>
    suspend fun memberProfileNFt(memberId: Long, page: Int, size: Int, sort: String): NetworkResult<Contents<CommonProfileNft>>
    suspend fun memberFollow(memberId: Long): NetworkResult<Unit>
    suspend fun commonFollow(page: Int, size: Int): NetworkResult<Contents<CommonFollow>>
    suspend fun commonFollowing(page: Int, size: Int): NetworkResult<Contents<CommonFollow>>
    suspend fun memberWallet(): NetworkResult<MemberWallet>
    suspend fun patchProfileImage(file: MultipartBody.Part): NetworkResult<Unit>
    suspend fun patchProfileDescription(description: String): NetworkResult<Unit>
    suspend fun patchProfileNickname(nickname: String): NetworkResult<Unit>
    suspend fun patchTempMemberPassword(): NetworkResult<Unit>
    suspend fun deleteNft(nftId: Long): NetworkResult<Unit>
    suspend fun memberSwap(page: Int, size: Int): NetworkResult<Contents<Swap>>
    suspend fun nftDetail(nftId: Long): NetworkResult<Nft>
    suspend fun recentNfts(page: Int, size: Int): NetworkResult<Contents<RecentNft>>
    suspend fun writePost(nftId: Long, title: String, content: String): NetworkResult<Unit>
    suspend fun patchPost(postId: Long, title: String, content: String): NetworkResult<Unit>
    suspend fun deletePost(postId: Long): NetworkResult<Unit>
    suspend fun posts(page: Int, size: Int): NetworkResult<Contents<Post>>
    suspend fun postsFromNft(nftId: Long, page: Int, size: Int): NetworkResult<Contents<Post>>
    suspend fun postDetail(postId: Long): NetworkResult<Post>
    suspend fun writePostComments(postId: Long, content: String): NetworkResult<Unit>
    suspend fun deletePostComments(commentId: Long): NetworkResult<Unit>
    suspend fun commentsFromPost(postId: Long, page: Int, size: Int): NetworkResult<Contents<Comment>>
    suspend fun hotPosts(page: Int, size: Int): NetworkResult<Contents<HotPost>>
    suspend fun ownNfts(page: Int, size: Int): NetworkResult<Contents<OwnNft>>
    suspend fun blockNft(nftId: Long): NetworkResult<Unit>
    suspend fun cancelBlockNft(nftId: Long): NetworkResult<Unit>
    suspend fun blockMember(memberId: Long): NetworkResult<Unit>
    suspend fun cancelBlockMember(memberId: Long): NetworkResult<Unit>
}
