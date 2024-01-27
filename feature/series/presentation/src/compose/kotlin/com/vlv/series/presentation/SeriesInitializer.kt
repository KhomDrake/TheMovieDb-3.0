package com.vlv.series.presentation

import androidx.startup.Initializer
import com.vlv.series.data.SeriesDataInitializer
import com.vlv.series.presentation.ui.SeriesListingViewModel
import com.vlv.series.presentation.ui.detail.SeriesDetailViewModel
import com.vlv.series.presentation.ui.detail.about.AboutSeriesViewModel
import com.vlv.series.presentation.ui.detail.cast.SeriesCastViewModel
import com.vlv.series.presentation.ui.detail.recommendations.SeriesRecommendationViewModel
import com.vlv.series.presentation.ui.detail.reviews.SeriesReviewViewModel
import com.vlv.series.presentation.ui.detail.seasons.SeasonsViewModel
import com.vlv.util.ModuleInitializer
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class SeriesInitializer: ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                viewModel { SeriesListingViewModel(get()) }
                viewModel { SeriesDetailViewModel(get()) }
                viewModel { SeriesCastViewModel(get()) }
                viewModel { SeriesReviewViewModel(get()) }
                viewModel { SeriesRecommendationViewModel(get()) }
                viewModel { AboutSeriesViewModel(androidApplication().resources, get()) }
                viewModel { SeasonsViewModel(androidApplication().resources, get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            SeriesDataInitializer::class.java
        )
    }

}