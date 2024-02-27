package com.vlv.tv_show

import android.content.Context
import androidx.startup.Initializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.tv_show.ui.search.SearchViewModel
import com.vlv.tv_show.ui.detail.TvShowDetailViewModel
import com.vlv.tv_show.ui.detail.about.AboutViewModel
import com.vlv.tv_show.ui.detail.cast.TvShowCastViewModel
import com.vlv.tv_show.ui.detail.recommendation.RecommendationViewModel
import com.vlv.tv_show.ui.detail.review.TvShowReviewViewModel
import com.vlv.tv_show.ui.detail.season.SeasonsViewModel
import com.vlv.tv_show.ui.listing.ListingTvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class TvShowInitializer: Initializer<Module> {

    override fun create(context: Context): Module {
        val module = module {
            viewModel { ListingTvShowViewModel(get()) }
            viewModel { SeasonsViewModel(get()) }
            viewModel { TvShowCastViewModel(get()) }
            viewModel { TvShowReviewViewModel(get()) }
            viewModel { AboutViewModel(get()) }
            viewModel { RecommendationViewModel(get()) }
            viewModel { TvShowDetailViewModel(get()) }
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