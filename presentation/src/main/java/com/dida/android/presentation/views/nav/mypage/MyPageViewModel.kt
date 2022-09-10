package com.dida.android.presentation.views.nav.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.util.SingleLiveEvent
import com.dida.data.repository.MainRepositoryImpl
import com.dida.domain.model.nav.mypage.UserCardsResponseModel
import com.dida.domain.onError
import com.dida.domain.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val mainRepositoryImpl: MainRepositoryImpl) : BaseViewModel() {

    private val TAG = "MyPageViewModel"

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _cardCntLiveData = MutableLiveData<Int>()
    val cardCntLiveData: LiveData<Int>
        get() = _cardCntLiveData

    private val _descriptionLiveData = MutableLiveData<String>()
    val descriptionLiveData: LiveData<String>
        get() = _descriptionLiveData

    private val _followerCntLiveData = MutableLiveData<Int>()
    val followerCntLiveData: LiveData<Int>
        get() = _followerCntLiveData

    private val _followingCntLiveData = MutableLiveData<Int>()
    val followingCntLiveData: LiveData<Int>
        get() = _followingCntLiveData

    private val _getWalletLiveData = MutableLiveData<Boolean>()
    val getWalletValue: Boolean
        get() = _getWalletLiveData.value?: false

    private val _nicknameLiveData = MutableLiveData<String>()
    val nicknameLiveData: LiveData<String>
        get() = _nicknameLiveData

    private val _profileUrlLiveData = MutableLiveData<String>()
    val profileUrlLiveData: LiveData<String>
        get() = _profileUrlLiveData

    private val _userCardsLiveData = SingleLiveEvent<List<UserCardsResponseModel>>()
    val userCardsLiveData: SingleLiveEvent<List<UserCardsResponseModel>>
        get() = _userCardsLiveData

    fun getUserProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            mainRepositoryImpl.getUserProfileAPI()
                .onSuccess {
                    _cardCntLiveData.postValue(it.cardCnt)
                    _descriptionLiveData.postValue(it.description)
                    _followerCntLiveData.postValue(it.followerCnt)
                    _followingCntLiveData.postValue(it.followingCnt)
                    _getWalletLiveData.postValue(it.getWallet)
                    _nicknameLiveData.postValue(it.nickname)
                    _profileUrlLiveData.postValue(it.profileUrl)
                }.onError {
                    _errorLiveData.postValue(it.message)
                }
        }
    }

    fun getUserCards(){
        viewModelScope.launch(Dispatchers.IO) {
            mainRepositoryImpl.getUserCardsAPI()
                .onSuccess {
                    _userCardsLiveData.postValue(it)
                }.onError {
                    //TODO : 잠시 테스트를 위해 임시 데이터를 넣어놨습니다.(추후에 삭제하기)
                    val exampleList = mutableListOf(
                        UserCardsResponseModel(0, "user name here", "NFT name here", "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "1.65"),
                        UserCardsResponseModel(1, "user name here", "NFT name here", "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "1.65"),
                        UserCardsResponseModel(2, "user name here", "NFT name here", "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "1.65"),
                        UserCardsResponseModel(3, "user name here", "NFT name here", "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", "1.65")
                    )
                    _userCardsLiveData.postValue(exampleList)
                    _errorLiveData.postValue(it.message)
                }
        }
    }
}