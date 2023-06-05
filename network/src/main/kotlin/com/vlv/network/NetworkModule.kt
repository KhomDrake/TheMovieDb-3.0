package com.vlv.network

import com.vlv.network.client.OkHttpFactory
import com.vlv.network.interceptors.InterceptorFactory
import com.vlv.network.moshi.MoshiFactory
import com.vlv.network.repository.MovieRepository
import com.vlv.network.retrofit.RetrofitFactory
import org.koin.dsl.module

val networkModule = module {
    single { MoshiFactory.moshi() }
    single { InterceptorFactory() }
    single { OkHttpFactory.create(get()) }
    single { RetrofitFactory.retrofit(get(), get()) }
    single { RetrofitFactory.service(get()) }

    single { MovieRepository(get()) }
}