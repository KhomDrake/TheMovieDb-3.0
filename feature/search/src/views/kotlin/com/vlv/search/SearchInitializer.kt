package com.vlv.search

import android.content.Context
import androidx.startup.Initializer
import com.vlv.network.NetworkInitializer
import com.vlv.network.di.ModuleInitializer
import com.vlv.search.ui.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class SearchInitializer : ModuleInitializer() {
    override fun create(context: Context): Module {
        val module = module {
            viewModel { SearchViewModel(get()) }
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