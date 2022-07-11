package com.dida.android.presentation.viewmodel.nav.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.GlobalApplication
import com.dida.android.data.repository.MainRepository
import com.dida.android.domain.model.nav.mypage.UserCardsResponseModel
import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val TAG = "MyPageViewModel"

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
            mainRepository.getUserProfile().let {
                if(it.isSuccessful){
                    Log.d(TAG, "getUserProfile: ${it.body()}")

                    val userProfileResponseModel = it.body()
                    if (userProfileResponseModel != null) {
                        _cardCntLiveData.postValue(userProfileResponseModel.cardCnt)
                        _descriptionLiveData.postValue(userProfileResponseModel.description)
                        _followerCntLiveData.postValue(userProfileResponseModel.followerCnt)
                        _followingCntLiveData.postValue(userProfileResponseModel.followingCnt)
                        _getWalletLiveData.postValue(userProfileResponseModel.getWallet)
                        _nicknameLiveData.postValue(userProfileResponseModel.nickname)
                        _profileUrlLiveData.postValue(userProfileResponseModel.profileUrl)
                    }
                }
                else{
                    Log.d(TAG, "error: ${it.code()}")
                }
            }
        }
    }

    fun getUserCards(){
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getUserCards().let {
                if(it.isSuccessful){
                    Log.d(TAG, "getUserCards: ${it.body()}")

                    val userCardsList = it.body()
                    if (userCardsList.isNullOrEmpty().not()) {
                        _userCardsLiveData.postValue(it.body())
                    }else{
                        //TODO : 잠시 테스트를 위해 임시 데이터를 넣어놨습니다.(추후에 삭제하기)
                        val exampleList = mutableListOf(
                            UserCardsResponseModel(0, "user name here", "NFT name here", "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", 1.65),
                            UserCardsResponseModel(1, "user name here", "NFT name here", "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", 1.65),
                            UserCardsResponseModel(2, "user name here", "NFT name here", "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", 1.65),
                            UserCardsResponseModel(3, "user name here", "NFT name here", "https://movie-phinf.pstatic.net/20190417_250/1555465284425i6WQE_JPEG/movie_image.jpg?type=m665_443_2", 1.65)
                        )
                        _userCardsLiveData.postValue(exampleList)
                    }
                }
                else{
                    Log.d(TAG, "error: ${it.code()}")
                }
            }
        }
    }
}