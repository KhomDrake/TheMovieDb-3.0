package com.vlv.people

import android.content.Context
import androidx.startup.Initializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.people.ui.detail.PeopleDetailViewModel
import com.vlv.people.ui.detail.about.AboutViewModel
import com.vlv.people.ui.detail.moviecredits.MovieCreditViewModel
import com.vlv.people.ui.detail.seriescredit.SeriesCreditViewModel
import com.vlv.people.ui.popular.PopularViewModel
import com.vlv.people.ui.search.SearchPeopleViewModel
import com.vlv.people.ui.trending.TrendingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class PeopleInitializer : Initializer<Module> {

    override fun create(context: Context): Module {
        val module = module {
            viewModel { PopularViewModel(get()) }
            viewModel { TrendingViewModel(get()) }
            viewModel { AboutViewModel(get()) }
            viewModel { MovieCreditViewModel(get()) }
            viewModel { SeriesCreditViewModel(get()) }
            viewModel { PeopleDetailViewModel(get()) }
            viewModel { SearchPeopleViewModel(get()) }
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