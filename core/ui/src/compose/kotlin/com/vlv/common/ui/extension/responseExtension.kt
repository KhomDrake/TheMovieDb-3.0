package com.vlv.common.ui.extension

import androidx.compose.runtime.Composable
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.ResponseStatus

@Composable
fun <Data> Response<Data>.handle(
    success: @Composable (Data) -> Unit,
    error: @Composable (Throwable?) -> Unit,
    loading: @Composable () -> Unit
) {
    when(this.state) {
        ResponseStatus.SUCCESS -> {
            this.data?.let {
                success.invoke(it)
            } ?: error.invoke(null)
        }
        ResponseStatus.ERROR -> {
            error.invoke(this.error)
        }
        else -> {
            loading.invoke()
        }
    }
}
