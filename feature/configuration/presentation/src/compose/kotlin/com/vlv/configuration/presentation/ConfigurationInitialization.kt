package com.vlv.configuration.presentation

import androidx.startup.Initializer
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module

class ConfigurationInitialization : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf()

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
    
}