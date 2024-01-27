package com.vlv.bondsmith.data

import br.com.arch.toolkit.common.DataResult
import br.com.arch.toolkit.common.DataResultStatus

enum class ResponseStatus {
    LOADING,
    ERROR,
    SUCCESS
}

data class Response<T>(
    val state: ResponseStatus = ResponseStatus.LOADING,
    val data: T? = null,
    val error: Throwable? = null
)

fun <Data> Response<Data>.toDataResult() = DataResult<Data>(
    data = data,
    error = error,
    status = when(state) {
        ResponseStatus.SUCCESS -> DataResultStatus.SUCCESS
        ResponseStatus.LOADING -> DataResultStatus.LOADING
        ResponseStatus.ERROR -> DataResultStatus.ERROR
    }
)

fun <Data> responseLoading() = Response<Data>()

fun <Data> responseData(data: Data?) = Response<Data>(
    state = ResponseStatus.SUCCESS,
    data = data
)

fun <Data> responseError(throwable: Throwable?) = Response<Data>(
    state = ResponseStatus.ERROR,
    error = throwable
)