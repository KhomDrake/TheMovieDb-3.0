package com.vlv.data.network.retrofit

import com.squareup.moshi.Moshi
import com.vlv.data.network.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.reflect.KClass

object RetrofitFactory {

    fun retrofit(okHttpClient: OkHttpClient, moshi: Moshi) : Retrofit {
        return Retrofit.Builder().apply {
            client(okHttpClient)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            baseUrl(BuildConfig.BASE_URL)
        }.build()
    }

    fun service(retrofit: Retrofit, kclass: KClass<*>) = retrofit.create(kclass.java)

}