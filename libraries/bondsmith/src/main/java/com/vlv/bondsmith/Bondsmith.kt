package com.vlv.bondsmith

import android.util.Log
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import br.com.arch.toolkit.livedata.response.ResponseLiveData
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.ResponseStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val bondsmithScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

class Bondsmith<Data>(private val scope: CoroutineScope) {

    private var execution: (suspend () -> Data)? = null

    private val _responseLiveData = MutableResponseLiveData<Data>()

    private val _mutableStateFlow = MutableStateFlow<Response<Data>>(Response<Data>())

    val responseLiveData: ResponseLiveData<Data>
        get() = _responseLiveData

    val responseStateFlow: StateFlow<Response<Data>>
        get() = _mutableStateFlow.asStateFlow()

    fun request(func: suspend () -> Data) = apply {
        execution = func
    }

    fun execute() = apply {
        scope.launch {
            _responseLiveData.postLoading()
            _mutableStateFlow.emit(
                _mutableStateFlow.value.copy(
                    state = ResponseStatus.LOADING
                )
            )
            runCatching {
                val data = execution?.invoke()
                    ?: return@runCatching run {
                        _responseLiveData.postError(Throwable())
                        _mutableStateFlow.emit(
                            _mutableStateFlow.value.copy(
                                state = ResponseStatus.ERROR,
                                error = Throwable()
                            )
                        )

                    }
                _responseLiveData.postData(data)
                _mutableStateFlow.emit(
                    _mutableStateFlow.value.copy(
                        state = ResponseStatus.SUCCESS,
                        data = data,
                        error = null
                    )
                )
            }.onFailure {
                _responseLiveData.postError(it)
                _mutableStateFlow.emitError(it)
            }
        }
    }

}

fun <T>bondsmith(scope: CoroutineScope = bondsmithScope) : Bondsmith<T> {
    return Bondsmith(scope)
}

inline fun <Data, NewData> StateFlow<Response<Data>>.mapData(
    crossinline transform: suspend (Data?) -> NewData?
) = map {
    val newData = transform.invoke(value.data)
    Response(
        data = newData,
        state = it.state,
        error = it.error
    )
}

suspend fun <Data> MutableStateFlow<Response<Data>>.emitError(throwable: Throwable) {
    Log.e("Vini", throwable.stackTraceToString())
    emit(
        value.copy(
            state = ResponseStatus.ERROR,
            error = throwable
        )
    )
}
