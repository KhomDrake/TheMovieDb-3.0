package com.vlv.util

import android.content.Context
import androidx.startup.Initializer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

abstract class ModuleInitializer : Initializer<Module> {

    abstract val modules: List<Module>
    open val shouldStartKoin: Boolean = false


    override fun create(context: Context): Module {
        if(shouldStartKoin) {
            startKoin {
                androidContext(context)
                modules(modules)
            }
        } else loadKoinModules(modules)
        return modules.firstOrNull() ?: module {  }
    }

}