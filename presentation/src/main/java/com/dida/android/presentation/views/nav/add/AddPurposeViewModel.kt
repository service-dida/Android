package com.dida.android.presentation.views.nav.add

import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.util.AppLog
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.KlaytnUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class AddPurposeViewModel @Inject constructor(private val klaytnUsecase: KlaytnUsecase) : BaseViewModel() {

    private val TAG = "AddPurposeViewModel"

    private val _errorLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean>
        get() = _errorLiveData

    private val _nftImageLiveData = MutableLiveData<String>()
    val nftImageLiveData: LiveData<String>
        get() = _nftImageLiveData

    private val _titleLiveData = MutableLiveData<String>()
    val titleLiveData: LiveData<String>
        get() = _titleLiveData

    private val _descriptionLiveData = MutableLiveData<String>()
    val descriptionLiveData: LiveData<String>
        get() = _descriptionLiveData

    /**
     * 소장용 : 1
     * 판매용 : 2
    * */
    private val _puposeTypeLiveData = MutableLiveData<Int>(0)
    val puposeTypeLiveData: LiveData<Int>
        get() = _puposeTypeLiveData

    fun initNFTInfo(imgUrl : String, title : String, description : String) {
        _nftImageLiveData.postValue(imgUrl)
        _titleLiveData.postValue(title)
        _descriptionLiveData.postValue(description)
    }

    fun changePurposeType(type : Int){
        _puposeTypeLiveData.postValue(type)
    }

    fun uploadAsset(imageFileName :  String, imagePath : String){
        val file = File(imagePath)

        val requestFile = file.asRequestBody(
            "image/*".toMediaTypeOrNull()
        )

        val requestBody = MultipartBody.Part.createFormData("file=@", file.name, requestFile)

        viewModelScope.launch {
            klaytnUsecase.uploadAsset(requestBody)
                .onSuccess {
                    it.catch { e ->
                        catchError(e)
                        AppLog.d("haha : $e")
                    }
                    it.collect { data ->
                        AppLog.d("haha : $data")
                        //UiState.Success(data)
                    }
                }.onError { e ->
                    catchError(e)
                    AppLog.d("haha : $e")
                }
        }
    }
}