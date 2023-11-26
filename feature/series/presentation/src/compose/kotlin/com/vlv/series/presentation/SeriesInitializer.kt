package com.vlv.series.presentation

import androidx.startup.Initializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.series.presentation.ui.SeriesListingViewModel
import com.vlv.util.ModuleInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class SeriesInitializer: ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                viewModel { SeriesListingViewModel(get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            NetworkInitializer::class.java
        )
    }

}