package com.vlv.search

import androidx.startup.Initializer
import com.vlv.search.domain.SearchDomainInitializer
import com.vlv.search.ui.SearchViewModel
import com.vlv.util.ModuleInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class SearchInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                viewModel { SearchViewModel(get(), get(), get(), get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            SearchDomainInitializer::class.java
        )
    }

}