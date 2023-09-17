package com.vlv.favorite

import android.content.Context
import androidx.startup.Initializer
import com.vlv.favorite.ui.movie.MovieFavoritesViewModel
import com.vlv.favorite.ui.series.SeriesFavoriteViewModel
import com.vlv.network.NetworkInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class FavoriteInitializer : Initializer<Module> {

    override fun create(context: Context): Module {
        val module = module {
            viewModel { MovieFavoritesViewModel(get()) }
            viewModel { SeriesFavoriteViewModel(get()) }
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