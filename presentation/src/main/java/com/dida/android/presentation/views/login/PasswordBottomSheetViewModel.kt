package com.dida.android.presentation.views.login

import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordBottomSheetViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

}