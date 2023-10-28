package com.vlv.network.di

import androidx.startup.Initializer
import org.koin.core.module.Module

abstract class ModuleInitializer : Initializer<Module> {

    init {
        Initializers.initializers.add(this)
    }

}