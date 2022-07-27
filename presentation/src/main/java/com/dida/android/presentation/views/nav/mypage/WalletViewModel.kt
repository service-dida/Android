package com.dida.android.presentation.views.nav.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val TAG = "WalletViewModel"

    /** NFT 거래 내역 타입 LiveData
     * 0 : 전체
     * 1 : 구매
     * 2 : 판매
     */
    private val _NFTHistoryFocusLiveData = MutableLiveData<Int>()
    val NFTHistoryFocusLiveData: LiveData<Int>
        get() = _NFTHistoryFocusLiveData

    fun changeNFTHistoryFocusType(type : Int){
        Log.d(TAG, "changeNFTHistoryFocusType: ${type}")
        _NFTHistoryFocusLiveData.postValue(type)
    }
}