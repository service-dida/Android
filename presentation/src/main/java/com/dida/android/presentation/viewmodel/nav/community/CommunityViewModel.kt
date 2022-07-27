package com.dida.android.presentation.viewmodel.nav.community

import com.dida.android.data.repository.MainRepository
import com.dida.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val TAG = "SNSViewModel"


}