package com.vlv.configuration

import android.content.Context
import androidx.startup.Initializer
import com.vlv.configuration.ui.SettingsViewModel
import com.vlv.network.NetworkInitializer
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class ConfigurationInitializer : Initializer<Module> {

    override fun create(context: Context): Module {
        val module = module {
            viewModel { SettingsViewModel(androidApplication().resources, get()) }
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