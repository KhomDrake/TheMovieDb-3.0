package com.vlv.series

import android.content.Context
import androidx.startup.Initializer
import com.vlv.network.NetworkInitializer
import com.vlv.series.ui.detail.about.AboutViewModel
import com.vlv.series.ui.listing.ListingSeriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class SeriesInitializer: Initializer<Module> {

    override fun create(context: Context): Module {
        val module = module {
            viewModel { ListingSeriesViewModel(get()) }
            viewModel { AboutViewModel(get()) }
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