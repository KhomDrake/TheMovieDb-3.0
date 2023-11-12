package com.vlv.data.common

import androidx.startup.Initializer
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module

class CommonInitializer : ModuleInitializer() {

    override val shouldStartKoin: Boolean
        get() = true

    override val modules: List<Module>
        get() = listOf()

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}