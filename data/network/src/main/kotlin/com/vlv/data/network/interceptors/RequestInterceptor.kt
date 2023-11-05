package com.vlv.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import com.vlv.data.network.BuildConfig

const val LANGUAGE_QUERY_PARAMETER = "language"
const val API_KEY_QUERY_PARAMETER = "api_key"

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .url(
                request.url
                    .newBuilder()
                    .apply {
                        addQueryParameter(
                            API_KEY_QUERY_PARAMETER,
                            BuildConfig.API_KEY
                        )
                        runCatching {
                            addQueryParameter(
                                LANGUAGE_QUERY_PARAMETER,
                                "en-US"
                            )
                        }
                    }
                    .build()
            )
            .build()

        return chain.proceed(newRequest)
    }
}