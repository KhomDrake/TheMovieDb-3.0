package com.vlv.movie

import android.content.Context
import androidx.startup.Initializer
import com.vlv.movie.ui.TrendingNowViewModel
import com.vlv.movie.ui.detail.about.AboutMovieViewModel
import com.vlv.movie.ui.detail.cast.CastViewModel
import com.vlv.movie.ui.search.SearchViewModel
import com.vlv.network.NetworkInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class MovieInitializer : Initializer<Module> {

    override fun create(context: Context): Module {
        val module = module {
            viewModel { TrendingNowViewModel(get()) }
            viewModel { SearchViewModel(get(), get()) }
            viewModel { AboutMovieViewModel(get()) }
            viewModel { CastViewModel(get()) }
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