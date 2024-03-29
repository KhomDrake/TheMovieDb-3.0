package com.vlv.network.client

import com.vlv.data.network.interceptors.InterceptorFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
object OkHttpFactory {
    fun create(interceptorFactory: InterceptorFactory) : OkHttpClient {
        return OkHttpClient().newBuilder().apply {
            interceptorFactory.interceptors().forEach {
                addInterceptor(it)
            }
            connectTimeout(60, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
        }.build()
    }
}