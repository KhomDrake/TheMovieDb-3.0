package com.vlv.network.retrofit

import com.squareup.moshi.Moshi
import com.vlv.network.api.MovieApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.reflect.KClass

object RetrofitFactory {

    fun retrofit(okHttpClient: OkHttpClient, moshi: Moshi) : Retrofit {
        return Retrofit.Builder().apply {
            client(okHttpClient)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            baseUrl("https://api.themoviedb.org/3/")
        }.build()
    }

    fun service(retrofit: Retrofit, kclass: KClass<*>) = retrofit.create(kclass.java)

}