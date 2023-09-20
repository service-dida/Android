package com.dida.hot_seller

import com.dida.common.base.BaseViewModel
import com.dida.common.util.PAGE_SIZE
import com.dida.domain.Contents
import com.dida.domain.main.model.HotSellerPage
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.HotSellerPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotSellerViewModel @Inject constructor(
    private val hotSellerPageUseCase: HotSellerPageUseCase
) : BaseViewModel(), HotSellerActionHandler {

    private val TAG = "HotSellerViewModel"

    private val _navigationEvent: MutableSharedFlow<HotSellerNavigationAction> = MutableSharedFlow<HotSellerNavigationAction>()
    val navigationEvent: SharedFlow<HotSellerNavigationAction> = _navigationEvent.asSharedFlow()

    private val _hotSellerState: MutableStateFlow<Contents<HotSellerPage>> = MutableStateFlow(
        Contents(page = 0, pageSize = 0, hasNext = true, content = emptyList())
    )

    val hotSellerState: StateFlow<Contents<HotSellerPage>> = _hotSellerState.asStateFlow()

    init {
        baseViewModelScope.launch {
            hotSellerPageUseCase(page = 0, size = PAGE_SIZE)
                .onSuccess { _hotSellerState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    fun beforePage() {
        baseViewModelScope.launch {
            if (hotSellerState.value.page == 0) return@launch
            showLoading()
            hotSellerPageUseCase.invoke(hotSellerState.value.page - 1, PAGE_SIZE)
                .onSuccess { _hotSellerState.value = it }
                .onError { e -> catchError(e) }
        }
    }

    fun nextPage() {
        baseViewModelScope.launch {
            if (!hotSellerState.value.hasNext) return@launch
            showLoading()
            hotSellerPageUseCase.invoke(hotSellerState.value.page + 1, PAGE_SIZE)
                .onSuccess { _hotSellerState.value = it }
                .onError { e -> catchError(e) }
            dismissLoading()
        }
    }

}
