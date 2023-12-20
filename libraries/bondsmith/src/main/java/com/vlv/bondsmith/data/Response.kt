package com.vlv.bondsmith.data

enum class ResponseStatus {
    IDLE,
    LOADING,
    ERROR,
    SUCCESS
}

data class Response<T>(
    val state: ResponseStatus = ResponseStatus.IDLE,
    val data: T? = null,
    val error: Throwable? = null
)