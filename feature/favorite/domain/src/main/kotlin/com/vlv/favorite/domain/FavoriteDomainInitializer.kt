package com.vlv.favorite.domain

import androidx.startup.Initializer
import com.vlv.favorite.data.FavoriteDataInitializer
import com.vlv.favorite.domain.usecase.FavoriteUseCase
import com.vlv.favorite.domain.usecase.MovieFavoriteUseCase
import com.vlv.favorite.domain.usecase.PeopleFavoriteUseCase
import com.vlv.favorite.domain.usecase.SeriesFavoriteUseCase
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class FavoriteDomainInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                factory { SeriesFavoriteUseCase(get()) }
                factory { PeopleFavoriteUseCase(get()) }
                factory { MovieFavoriteUseCase(get()) }
                factory { FavoriteUseCase(get()) }
            }
        )


    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            FavoriteDataInitializer::class.java
        )
    }
}