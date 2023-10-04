package com.dida.soldout

import com.dida.common.base.BaseViewModel
import com.dida.common.util.INIT_PAGE
import com.dida.common.util.PAGE_SIZE
import com.dida.data.DataApplication
import com.dida.domain.Contents
import com.dida.domain.main.model.SoldOut
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.SoldOutPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoldOutViewModel @Inject constructor(
    private val soldOutPageUseCase: SoldOutPageUseCase
) : BaseViewModel() {

    private val TAG = "SoldOutViewModel"

    private val _periodState: MutableStateFlow<List<Period>> = MutableStateFlow(listOf(Period.SEVEN_DAY, Period.ONE_MONTH, Period.SIX_MONTH, Period.ONE_YEAR))
    val periodState: StateFlow<List<Period>> = _periodState.asStateFlow()

    private val _selectedPeriod: MutableStateFlow<Int> = MutableStateFlow(Period.SEVEN_DAY.period)
    val selectedPeriod: StateFlow<Int> = _selectedPeriod.asStateFlow()

    private val _soldOutState: MutableStateFlow<Contents<SoldOut>> = MutableStateFlow(
        Contents(page = INIT_PAGE, pageSize = PAGE_SIZE, hasNext = true, content = emptyList())
    )
    val soldOutState: StateFlow<Contents<SoldOut>> = _soldOutState.asStateFlow()

    fun getSoldOut(period: Int) {
        baseViewModelScope.launch {
            soldOutPageUseCase(range = period, page = INIT_PAGE, size = PAGE_SIZE)
                .onSuccess {
                    _selectedPeriod.value = period
                    _soldOutState.value = it
                }
                .onError { e -> catchError(e) }
        }
    }

    fun onSelectPeriod(period: Int) {
        baseViewModelScope.launch {
            soldOutPageUseCase(range = period, page = INIT_PAGE, size = PAGE_SIZE)
                .onSuccess {
                    _selectedPeriod.value = period
                    _soldOutState.value = it
                }
                .onError { e -> catchError(e) }
        }
    }

    fun onNextPageFromSoldOut() {
        baseViewModelScope.launch {
            if (!soldOutState.value.hasNext) return@launch
            soldOutPageUseCase(page = soldOutState.value.page + 1, size = PAGE_SIZE, range = selectedPeriod.value)
                .onSuccess {
                    it.content = (soldOutState.value.content.toMutableList()) + it.content
                    _soldOutState.value = it
                }
                .onError { e -> catchError(e) }
        }
    }

}
