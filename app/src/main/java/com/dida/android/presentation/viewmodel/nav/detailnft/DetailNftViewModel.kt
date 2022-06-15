package com.dida.android.presentation.viewmodel.nav.detailnft

import com.dida.android.data.repository.MainRepository
import com.dida.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailNftViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val TAG = "DetailNftViewModel"


}