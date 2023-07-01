package com.vlv.network

import android.content.Context
import androidx.startup.Initializer
import com.vlv.network.client.OkHttpFactory
import com.vlv.network.interceptors.InterceptorFactory
import com.vlv.network.moshi.MoshiFactory
import com.vlv.network.repository.MovieRepository
import com.vlv.network.retrofit.RetrofitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class NetworkInitializer : Initializer<Module> {

    override fun create(context: Context): Module {
        val module = module {
            single { MoshiFactory.moshi() }
            single { InterceptorFactory() }
            single { OkHttpFactory.create(get()) }
            single { RetrofitFactory.retrofit(get(), get()) }
            single { RetrofitFactory.service(get()) }

            single { MovieRepository(get()) }
        }

        startKoin {
            androidContext(context)
            modules(module)
        }

        return module
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}