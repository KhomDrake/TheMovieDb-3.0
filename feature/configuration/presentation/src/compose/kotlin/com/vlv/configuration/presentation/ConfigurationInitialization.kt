package com.vlv.configuration.presentation

import androidx.startup.Initializer
import com.vlv.configuration.domain.ConfigurationDomainInitializer
import com.vlv.configuration.presentation.ui.SettingsViewModel
import com.vlv.util.ModuleInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class ConfigurationInitialization : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                viewModel {
                    SettingsViewModel(get())
                }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            ConfigurationDomainInitializer::class.java
        )
    }
    
}