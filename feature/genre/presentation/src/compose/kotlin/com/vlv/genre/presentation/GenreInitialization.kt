package com.vlv.genre.presentation

import androidx.startup.Initializer
import com.vlv.genre.domain.GenreDomainInitializer
import com.vlv.genre.presentation.ui.movie.MovieGenreViewModel
import com.vlv.genre.presentation.ui.series.SeriesGenreViewModel
import com.vlv.util.ModuleInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class GenreInitialization : ModuleInitializer() {
    override val modules: List<Module>
        get() = listOf(
            module {
                viewModel { MovieGenreViewModel(get()) }
                viewModel { SeriesGenreViewModel(get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            GenreDomainInitializer::class.java
        )
    }
}