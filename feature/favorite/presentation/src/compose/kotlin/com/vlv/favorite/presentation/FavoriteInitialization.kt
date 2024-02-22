package com.vlv.favorite.presentation

import androidx.startup.Initializer
import com.vlv.favorite.domain.FavoriteDomainInitializer
import com.vlv.favorite.presentation.ui.movie.MovieFavoriteViewModel
import com.vlv.favorite.presentation.ui.people.PeopleFavoriteViewModel
import com.vlv.favorite.presentation.ui.series.SeriesFavoriteViewModel
import com.vlv.util.ModuleInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class FavoriteInitialization : ModuleInitializer() {
    override val modules: List<Module>
        get() = listOf(
            module {
                viewModel { MovieFavoriteViewModel(get()) }
                viewModel { SeriesFavoriteViewModel(get()) }
                viewModel { PeopleFavoriteViewModel(get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            FavoriteDomainInitializer::class.java
        )
    }
}