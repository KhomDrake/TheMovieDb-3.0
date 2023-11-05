package com.vlv.search.domain

import androidx.startup.Initializer
import com.vlv.search.data.SearchDataInitializer
import com.vlv.search.domain.usecase.HistoryUseCase
import com.vlv.search.domain.usecase.SearchMovieUseCase
import com.vlv.search.domain.usecase.SearchPeopleUseCase
import com.vlv.search.domain.usecase.SearchSeriesUseCase
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class SearchDomainInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                factory { HistoryUseCase(get()) }
                factory { SearchPeopleUseCase(get()) }
                factory { SearchSeriesUseCase(get()) }
                factory { SearchMovieUseCase(get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            SearchDataInitializer::class.java
        )
    }

}