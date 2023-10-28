package com.vlv.genre

import android.content.Context
import androidx.startup.Initializer
import com.vlv.genre.ui.movie.MovieGenreViewModel
import com.vlv.genre.ui.series.SeriesGenreViewModel
import com.vlv.network.NetworkInitializer
import com.vlv.network.di.ModuleInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class GenreInitialization : ModuleInitializer() {


    override fun create(context: Context): Module {
        val module = module {
            viewModel { MovieGenreViewModel(get()) }
            viewModel { SeriesGenreViewModel(get()) }
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