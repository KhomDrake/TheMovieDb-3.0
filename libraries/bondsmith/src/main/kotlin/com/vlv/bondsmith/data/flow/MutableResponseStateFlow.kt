package com.vlv.bondsmith.data.flow

import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.ResponseStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MutableResponseStateFlow<Data>(
    previous: Response<Data> = Response(),
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
) : MutableStateFlow<Response<Data>>, ResponseStateFlow<Data>(previous, scope) {

    suspend fun emitSuccess(data: Data?) {
        flowState.emit(
            flowState.value.copy(
                data = data,
                state = ResponseStatus.SUCCESS,
                error = null
            )
        )
    }

    suspend fun emitLoading() {
        flowState.emit(
            flowState.value.copy(
                state = ResponseStatus.LOADING,
                error = null
            )
        )
    }

    suspend fun emitError(throwable: Throwable?) {
        flowState.emit(
            flowState.value.copy(
                state = ResponseStatus.ERROR,
                error = throwable
            )
        )
    }

    override val subscriptionCount: StateFlow<Int>
        get() = flowState.subscriptionCount

    override suspend fun emit(value: Response<Data>) {
        flowState.emit(value)
    }

    override fun compareAndSet(expect: Response<Data>, update: Response<Data>): Boolean {
        return flowState.compareAndSet(expect, update)
    }

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() {
        flowState.resetReplayCache()
    }

    override fun tryEmit(value: Response<Data>): Boolean {
        return flowState.tryEmit(value)
    }

}