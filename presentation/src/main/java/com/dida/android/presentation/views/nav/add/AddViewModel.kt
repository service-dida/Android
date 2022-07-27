package com.dida.android.presentation.views.nav.add

import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val TAG = "AddViewModel"


}