package com.vlv.network.client

import br.com.mrocigno.bigbrother.network.BigBrotherInterceptor
import com.gustafah.android.mockinterceptor.MockConfig
import com.gustafah.android.mockinterceptor.MockInterceptor
import com.vlv.network.interceptors.InterceptorFactory
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import java.util.concurrent.TimeUnit

object OkHttpFactory : KoinComponent {

    fun create(interceptorFactory: InterceptorFactory) : OkHttpClient {
        return OkHttpClient().newBuilder().apply {
            interceptorFactory.interceptors().forEach {
                addInterceptor(it)
            }

            if(EnvConfig.inMock) {
                addInterceptor(
                    MockInterceptor.apply {
                        config = MockConfig.Builder()
                            .suffix(".json")
                            .separator("_")
                            .prefix("mock/")
                            .context { ContextProvider.context.get()!! }
                            .selectorMode(MockConfig.OptionsSelectorMode.STANDARD)
                            .build()
                    }
                )
            }

            addInterceptor(BigBrotherInterceptor())

            connectTimeout(60, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
        }.build()
    }
}