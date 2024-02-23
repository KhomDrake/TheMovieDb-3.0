package com.vlv.tv_show.presentation

import androidx.startup.Initializer
import com.vlv.tv_show.data.TvShowDataInitializer
import com.vlv.tv_show.presentation.ui.TvShowListingViewModel
import com.vlv.tv_show.presentation.ui.detail.SeriesDetailViewModel
import com.vlv.tv_show.presentation.ui.detail.about.AboutTvShowViewModel
import com.vlv.tv_show.presentation.ui.detail.cast.TvShowCastViewModel
import com.vlv.tv_show.presentation.ui.detail.recommendations.TvShowsRecommendationViewModel
import com.vlv.tv_show.presentation.ui.detail.reviews.TvShowReviewViewModel
import com.vlv.tv_show.presentation.ui.detail.seasons.SeasonsViewModel
import com.vlv.util.ModuleInitializer
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class TvShowsInitializer: ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                viewModel { TvShowListingViewModel(get()) }
                viewModel { SeriesDetailViewModel(get()) }
                viewModel { TvShowCastViewModel(get()) }
                viewModel { TvShowReviewViewModel(get()) }
                viewModel { TvShowsRecommendationViewModel(get()) }
                viewModel { AboutTvShowViewModel(androidApplication().resources, get()) }
                viewModel { SeasonsViewModel(androidApplication().resources, get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            TvShowDataInitializer::class.java
        )
    }

}