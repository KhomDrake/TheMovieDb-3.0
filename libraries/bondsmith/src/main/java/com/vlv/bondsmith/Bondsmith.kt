package com.vlv.bondsmith

import android.util.Log
import br.com.arch.toolkit.common.DataResult
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import br.com.arch.toolkit.livedata.response.ResponseLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

class Bondsmith<Data>(private val scope: CoroutineScope) {

    private var execution: (suspend () -> Data)? = null

    private val _responseLiveData = MutableResponseLiveData<Data>()

    val responseLiveData: ResponseLiveData<Data>
        get() = _responseLiveData

    fun request(func: suspend () -> Data) = apply {
        execution = func
    }

    fun execute() = apply {
        scope.launch {
            _responseLiveData.postLoading()
            runCatching {
                val data = execution?.invoke()
                    ?: return@runCatching _responseLiveData.postError(Throwable())
                _responseLiveData.postData(data)
            }.onFailure {
                Log.i("Vini", it.stackTraceToString())
                _responseLiveData.postError(it)
            }
        }
    }

}

fun <T>bondsmith(scope: CoroutineScope = coroutineScope) : Bondsmith<T> {
    return Bondsmith(scope)
}