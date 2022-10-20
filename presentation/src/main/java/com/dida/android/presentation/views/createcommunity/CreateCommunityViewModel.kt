package com.dida.android.presentation.views.createcommunity

import com.dida.android.presentation.base.BaseViewModel
import com.dida.data.repository.MainRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateCommunityViewModel @Inject constructor(
    private val mainRepositoryImpl: MainRepositoryImpl
) : BaseViewModel() {
    private val TAG = "CreateCommunityViewModel"

}