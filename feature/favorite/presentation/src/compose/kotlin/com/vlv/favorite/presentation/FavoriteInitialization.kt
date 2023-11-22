package com.vlv.favorite.presentation

import androidx.startup.Initializer
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module

class FavoriteInitialization : ModuleInitializer() {
    override val modules: List<Module>
        get() = listOf()

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}