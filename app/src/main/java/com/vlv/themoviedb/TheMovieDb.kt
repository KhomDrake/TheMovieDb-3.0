package com.vlv.themoviedb

import android.app.Application
import com.vlv.movie.ui.search.SearchViewModel
import com.vlv.network.networkModule
import com.vlv.themoviedb.ui.movie.favorites.MovieFavoritesViewModel
import com.vlv.themoviedb.ui.movie.nowplaying.NowPlayingViewModel
import com.vlv.themoviedb.ui.movie.trending.TrendingNowViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    viewModel { NowPlayingViewModel(get()) }
    viewModel { MovieFavoritesViewModel() }
    viewModel { TrendingNowViewModel(get()) }
    viewModel { com.vlv.movie.ui.TrendingNowViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}

class TheMovieDb : Application() {

    override fun onCreate() {
        super.onCreate()
        configKoin()
    }

    private fun configKoin() {
        startKoin {
            androidLogger()
            androidContext(this@TheMovieDb)
            modules(
                networkModule + appModule
            )
        }
    }

}