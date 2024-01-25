package com.dida.soldout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dida.common.base.BaseViewModel
import com.dida.common.util.FIRST_PAGE
import com.dida.common.util.PAGE_SIZE
import com.dida.domain.Contents
import com.dida.domain.main.model.SoldOut
import com.dida.domain.onError
import com.dida.domain.onSuccess
import com.dida.domain.usecase.SoldOutPageUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SoldOutViewModel @AssistedInject constructor(
    @Assisted("period") val period: Int,
    private val soldOutPageUseCase: SoldOutPageUseCase
) : BaseViewModel() {

    private val TAG = "SoldOutViewModel"

    private val _periodState: MutableStateFlow<List<Period>> = MutableStateFlow(listOf(Period.SEVEN_DAY, Period.ONE_MONTH, Period.SIX_MONTH, Period.ONE_YEAR))
    val periodState: StateFlow<List<Period>> = _periodState.asStateFlow()

    private val _selectedPeriod: MutableStateFlow<Int> = MutableStateFlow(Period.SEVEN_DAY.period)
    val selectedPeriod: StateFlow<Int> = _selectedPeriod.asStateFlow()

    private val _soldOutState: MutableStateFlow<Contents<SoldOut>> = MutableStateFlow(
        Contents(page = FIRST_PAGE, pageSize = PAGE_SIZE, hasNext = true, content = emptyList())
    )
    val soldOutState: StateFlow<Contents<SoldOut>> = _soldOutState.asStateFlow()

    init {
        baseViewModelScope.launch {
            soldOutPageUseCase(range = period, page = FIRST_PAGE, size = PAGE_SIZE)
                .onSuccess {
                    _selectedPeriod.value = period
                    _soldOutState.value = it
                }
                .onError { e -> catchError(e) }
        }
    }

    fun onSelectPeriod(period: Int) {
        baseViewModelScope.launch {
            soldOutPageUseCase(range = period, page = FIRST_PAGE, size = PAGE_SIZE)
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
                }.onError { e -> catchError(e) }
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory{
        fun create(
            @Assisted("period") period: Int
        ): SoldOutViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            period: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(period) as T
            }
        }
    }

}
