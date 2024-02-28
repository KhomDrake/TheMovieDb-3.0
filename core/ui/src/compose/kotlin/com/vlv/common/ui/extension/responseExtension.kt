package com.vlv.common.ui.extension

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.ResponseStatus

@Composable
fun <Data> Response<Data>.handle(
    success: @Composable (Data) -> Unit = {},
    error: @Composable (Throwable?) -> Unit = {},
    loading: @Composable () -> Unit = {}
) {
    val response = this
    AnimatedContent(
        targetState = this.state,
        label = "Content"
    ) { state ->
        when(state) {
            ResponseStatus.SUCCESS -> {
                response.data?.let {
                    success.invoke(it)
                } ?: error.invoke(null)
            }
            ResponseStatus.ERROR -> {
                error.invoke(response.error)
            }
            ResponseStatus.LOADING -> {
                loading.invoke()
            }
        }
    }

}
