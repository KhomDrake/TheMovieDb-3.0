package com.vlv.people

import androidx.startup.Initializer
import com.vlv.favorite.domain.FavoriteDomainInitializer
import com.vlv.people.data.PeopleDataInitializer
import com.vlv.people.ui.detail.PeopleFavoriteViewModel
import com.vlv.people.ui.detail.tab.PeopleAboutViewModel
import com.vlv.people.ui.detail.tab.PeopleCreditViewModel
import com.vlv.people.ui.listing.PeopleListingViewModel
import com.vlv.util.ModuleInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class PeopleInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                viewModel { PeopleFavoriteViewModel(get()) }
                viewModel { PeopleAboutViewModel(get()) }
                viewModel { PeopleListingViewModel(get()) }
                viewModel { PeopleCreditViewModel(get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            FavoriteDomainInitializer::class.java,
            PeopleDataInitializer::class.java
        )
    }

}