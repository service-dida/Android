package com.dida.common.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NoCompareMutableStateFlow<T>(
    value: T
) : MutableStateFlow<T> {

    override var value: T = value
        set(value) {
            field = value
            innerFlow.tryEmit(value)
        }

    private val innerFlow = MutableSharedFlow<T>(replay = 1)

    override fun compareAndSet(expect: T, update: T): Boolean {
        value = update
        return true
    }

    override suspend fun emit(value: T) {
        this.value = value
    }

    override fun tryEmit(value: T): Boolean {
        this.value = value
        return true
    }

    override val subscriptionCount: StateFlow<Int> = innerFlow.subscriptionCount
    @ExperimentalCoroutinesApi
    override fun resetReplayCache() = innerFlow.resetReplayCache()
    override suspend fun collect(collector: FlowCollector<T>): Nothing = innerFlow.collect(collector)
    override val replayCache: List<T> = innerFlow.replayCache
}
