package com.vlv.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .url(
                request.url
                    .newBuilder()
                    .addQueryParameter("api_key", "ed84e9c8c38d4d0a8f3adaa5ba324145")
                    .build()
            )
            .build()

        return chain.proceed(newRequest)
    }
}