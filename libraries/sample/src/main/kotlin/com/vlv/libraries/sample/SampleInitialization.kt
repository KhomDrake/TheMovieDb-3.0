package com.vlv.libraries.sample

import androidx.startup.Initializer
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module

class SampleInitialization : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf()

    override val shouldStartKoin: Boolean
        get() = true

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}