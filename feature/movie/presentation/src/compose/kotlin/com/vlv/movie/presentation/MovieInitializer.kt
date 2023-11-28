package com.vlv.movie.presentation

import androidx.startup.Initializer
import com.vlv.favorite.domain.FavoriteDomainInitializer
import com.vlv.movie.data.MovieDataInitializer
import com.vlv.movie.presentation.ui.MovieListingViewModel
import com.vlv.movie.presentation.ui.detail.MovieDetailViewModel
import com.vlv.util.ModuleInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class MovieInitializer: ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                viewModel { MovieListingViewModel(get()) }
                viewModel { MovieDetailViewModel(get(), get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            MovieDataInitializer::class.java,
            FavoriteDomainInitializer::class.java
        )
    }

}