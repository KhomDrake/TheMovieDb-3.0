package com.vlv.movie.presentation

import android.content.Context
import androidx.startup.Initializer
import com.vlv.favorite.domain.FavoriteDomainInitializer
import com.vlv.movie.data.MovieDataInitializer
import com.vlv.movie.presentation.ui.detail.MovieDetailViewModel
import com.vlv.movie.presentation.ui.detail.about.AboutMovieViewModel
import com.vlv.movie.presentation.ui.detail.cast.CastViewModel
import com.vlv.movie.presentation.ui.detail.recommendation.RecommendationViewModel
import com.vlv.movie.presentation.ui.detail.review.ReviewViewModel
import com.vlv.movie.presentation.ui.listing.ListingMovieViewModel
import com.vlv.movie.presentation.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class MovieInitializer : Initializer<Module> {

    override fun create(context: Context): Module {
        val module = module {
            viewModel { ListingMovieViewModel(get()) }
            viewModel { SearchViewModel(get(), get(), get(), get()) }
            viewModel { AboutMovieViewModel(get()) }
            viewModel { CastViewModel(get()) }
            viewModel { ReviewViewModel(get()) }
            viewModel { RecommendationViewModel(get()) }
            viewModel { MovieDetailViewModel(get()) }
        }
        loadKoinModules(module)
        return module
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            MovieDataInitializer::class.java,
            FavoriteDomainInitializer::class.java
        )
    }

}