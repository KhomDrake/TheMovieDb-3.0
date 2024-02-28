package com.vlv.themoviedb.ui

import androidx.startup.Initializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.themoviedb.ui.menu.MenuViewModel
import com.vlv.themoviedb.ui.movie.NowPlayingViewModel
import com.vlv.themoviedb.ui.movie.TrendingViewModel
import com.vlv.themoviedb.ui.series.AiringTodayViewModel
import com.vlv.themoviedb.ui.series.SeriesTrendingViewModel
import com.vlv.util.ModuleInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class MainInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                viewModel { NowPlayingViewModel(get()) }
                viewModel { TrendingViewModel(get()) }
                viewModel { MenuViewModel() }
                viewModel { SeriesTrendingViewModel(get()) }
                viewModel { AiringTodayViewModel(get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            NetworkInitializer::class.java
        )
    }

}