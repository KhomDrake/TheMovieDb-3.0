package com.vlv.search

import androidx.startup.Initializer
import com.vlv.search.domain.SearchDomainInitializer
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class SearchInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {

            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            SearchDomainInitializer::class.java
        )
    }

}