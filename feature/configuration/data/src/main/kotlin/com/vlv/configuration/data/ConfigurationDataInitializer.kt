package com.vlv.configuration.data

import androidx.startup.Initializer
import com.vlv.configuration.data.api.ConfigurationApi
import com.vlv.data.database.DatabaseInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.data.network.retrofit.RetrofitFactory
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class ConfigurationDataInitializer : ModuleInitializer() {
    override val modules: List<Module>
        get() = listOf(module {
            single { RetrofitFactory.service(get(), ConfigurationApi::class) as ConfigurationApi }
            single { ConfigurationRepository(get(), get()) }
        })

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            NetworkInitializer::class.java,
            DatabaseInitializer::class.java
        )
    }

}