package com.vlv.themoviedb

import android.app.Application
import com.vlv.network.networkModule
import com.vlv.themoviedb.ui.movie.MovieViewModel
import com.vlv.themoviedb.ui.movie.NowPlayingViewModel
import com.vlv.themoviedb.ui.movie.TrendingNowViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { NowPlayingViewModel(get()) }
    viewModel { TrendingNowViewModel(get()) }
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