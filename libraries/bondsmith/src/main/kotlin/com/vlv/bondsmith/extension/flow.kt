package com.vlv.bondsmith.extension

import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.ResponseStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

inline fun <Data, NewData> Flow<Response<Data>>.mapData(
    crossinline transform: suspend (Data?) -> NewData?
) = map {
    val newData = transform.invoke(it.data)
    Response(
        data = newData,
        state = it.state,
        error = it.error
    )
}

inline fun <Data> Flow<Response<Data>>.mapError(
    crossinline transform: suspend (Throwable?) -> Throwable?
) = map {
    val newError = transform.invoke(it.error)
    Response(
        data = it.data,
        state = it.state,
        error = newError
    )
}

suspend fun <Data> MutableStateFlow<Response<Data>>.emitError(throwable: Throwable) {
    emit(
        value.copy(
            state = ResponseStatus.ERROR,
            error = throwable
        )
    )
}
