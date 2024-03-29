package com.vlv.themoviedb.ui

import android.content.Context
import androidx.startup.Initializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.themoviedb.ui.menu.MenuViewModel
import com.vlv.themoviedb.ui.movie.nowplaying.NowPlayingViewModel
import com.vlv.themoviedb.ui.movie.trending.TrendingNowViewModel
import com.vlv.themoviedb.ui.tv_show.airingtoday.AiringTodayViewModel
import com.vlv.themoviedb.ui.tv_show.trending.TrendingTvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class MainInitializer : Initializer<Module> {

    override fun create(context: Context): Module {
        val module = module {
            viewModel { NowPlayingViewModel(get()) }
            viewModel { TrendingNowViewModel(get()) }
            viewModel { TrendingTvShowViewModel(get()) }
            viewModel { AiringTodayViewModel(get()) }
            viewModel { MenuViewModel() }
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