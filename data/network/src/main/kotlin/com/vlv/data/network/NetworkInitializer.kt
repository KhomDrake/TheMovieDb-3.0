package com.vlv.data.network

import androidx.startup.Initializer
import com.vlv.network.client.OkHttpFactory
import com.vlv.data.network.interceptors.InterceptorFactory
import com.vlv.data.network.moshi.MoshiFactory
import com.vlv.data.network.retrofit.RetrofitFactory
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class NetworkInitializer : ModuleInitializer() {

    override val shouldStartKoin: Boolean
        get() = true

    override val modules: List<Module>
        get() = listOf(
            moshiModule(),
            interceptorModule(),
            okHttpModule(),
            retrofitModule()
        )

    private fun moshiModule() = module {
        single { MoshiFactory.moshi() }
    }

    private fun interceptorModule() = module {
        single { InterceptorFactory() }
    }

    private fun okHttpModule() = module {
        single { OkHttpFactory.create(get()) }
    }

    private fun retrofitModule() = module {
        single { RetrofitFactory.retrofit(get(), get()) }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}