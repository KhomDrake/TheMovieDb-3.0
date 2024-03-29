package com.vlv.genre

import android.content.Context
import androidx.startup.Initializer
import com.vlv.genre.domain.GenreDomainInitializer
import com.vlv.genre.presentation.ui.movie.MovieGenreViewModel
import com.vlv.genre.presentation.ui.tv_show.TvShowGenreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class GenreInitialization : Initializer<Module> {


    override fun create(context: Context): Module {
        val module = module {
            viewModel { MovieGenreViewModel(get()) }
            viewModel { TvShowGenreViewModel(get()) }
        }

        loadKoinModules(module)

        return module
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            GenreDomainInitializer::class.java
        )
    }

}