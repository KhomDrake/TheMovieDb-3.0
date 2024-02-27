package com.vlv.genre.domain

import androidx.startup.Initializer
import com.vlv.genre.data.GenreDataInitializer
import com.vlv.genre.domain.usecase.MovieGenreUseCase
import com.vlv.genre.domain.usecase.TvShowGenreUseCase
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class GenreDomainInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                factory { MovieGenreUseCase(get()) }
                factory { TvShowGenreUseCase(get()) }
            }
        )


    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            GenreDataInitializer::class.java
        )
    }
}