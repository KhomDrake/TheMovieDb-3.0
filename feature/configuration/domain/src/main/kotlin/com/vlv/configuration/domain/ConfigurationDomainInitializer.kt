package com.vlv.configuration.domain

import androidx.startup.Initializer
import com.vlv.configuration.data.ConfigurationDataInitializer
import com.vlv.configuration.domain.usecase.SettingsUseCase
import com.vlv.configuration.domain.usecase.SetupConfigurationUseCase
import com.vlv.util.ModuleInitializer
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

class ConfigurationDomainInitializer : ModuleInitializer() {
    override val modules: List<Module>
        get() = listOf(module {
            factory { SettingsUseCase(androidApplication().resources, get()) }
            factory { SetupConfigurationUseCase(get()) }
        })

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            ConfigurationDataInitializer::class.java
        )
    }

}