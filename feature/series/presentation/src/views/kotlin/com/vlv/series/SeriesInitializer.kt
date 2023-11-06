package com.vlv.series

import android.content.Context
import androidx.startup.Initializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.series.ui.detail.SeriesDetailViewModel
import com.vlv.series.ui.detail.about.AboutViewModel
import com.vlv.series.ui.detail.cast.SeriesCastViewModel
import com.vlv.series.ui.detail.recommendation.RecommendationViewModel
import com.vlv.series.ui.detail.review.SeriesReviewViewModel
import com.vlv.series.ui.detail.season.SeasonsViewModel
import com.vlv.series.ui.listing.ListingSeriesViewModel
import com.vlv.series.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class SeriesInitializer: Initializer<Module> {

    override fun create(context: Context): Module {
        val module = module {
            viewModel { ListingSeriesViewModel(get()) }
            viewModel { SeasonsViewModel(get()) }
            viewModel { SeriesCastViewModel(get()) }
            viewModel { SeriesReviewViewModel(get()) }
            viewModel { AboutViewModel(get()) }
            viewModel { RecommendationViewModel(get()) }
            viewModel { SeriesDetailViewModel(get()) }
            viewModel { SearchViewModel(get(), get(), get(), get()) }
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