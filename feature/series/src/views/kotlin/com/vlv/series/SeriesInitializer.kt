package com.vlv.series

import android.content.Context
import androidx.startup.Initializer
import com.vlv.network.NetworkInitializer
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class SeriesInitializer: Initializer<Module> {

    override fun create(context: Context): Module {
        val module = module {
        }
        loadKoinModules(module)
        return module
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            NetworkInitializer::class.java
        )
    }

}