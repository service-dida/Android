package com.dida.android.presentation.views.nav.detailnft

import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailNftViewModel @Inject constructor(private val mainRepositoryImpl: MainRepositoryImpl) : BaseViewModel() {

    private val TAG = "DetailNftViewModel"


}