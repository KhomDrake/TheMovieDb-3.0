package com.vlv.movie

import android.content.Context
import androidx.startup.Initializer
import com.vlv.movie.ui.detail.MovieDetailViewModel
import com.vlv.movie.ui.detail.about.AboutMovieViewModel
import com.vlv.movie.ui.detail.cast.CastViewModel
import com.vlv.movie.ui.detail.recommendation.RecommendationViewModel
import com.vlv.movie.ui.detail.review.ReviewViewModel
import com.vlv.movie.ui.listing.ListingMovieViewModel
import com.vlv.movie.ui.search.SearchViewModel
import com.vlv.network.NetworkInitializer
import com.vlv.network.di.ModuleInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class MovieInitializer : ModuleInitializer() {

    override fun create(context: Context): Module {
        val module = module {
            viewModel { ListingMovieViewModel(get()) }
            viewModel { SearchViewModel(get()) }
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
            NetworkInitializer::class.java
        )
    }

}