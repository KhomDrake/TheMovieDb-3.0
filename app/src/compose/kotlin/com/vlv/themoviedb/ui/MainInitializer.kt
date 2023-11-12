package com.vlv.themoviedb.ui

import androidx.startup.Initializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module

class MainInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf()

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            NetworkInitializer::class.java
        )
    }

}