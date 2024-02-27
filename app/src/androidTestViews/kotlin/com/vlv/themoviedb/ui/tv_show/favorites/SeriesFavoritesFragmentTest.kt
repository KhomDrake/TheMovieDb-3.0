package com.vlv.themoviedb.ui.tv_show.favorites

import com.vlv.favorite.FavoriteInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.favorite.domain.usecase.SeriesFavoriteUseCase
import com.vlv.tv_show.SeriesInitializer
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import com.vlv.themoviedb.ui.MainInitializer
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<SeriesFavoriteUseCase>(relaxed = true) }
}

class SeriesFavoritesFragmentTest {

    @get:Rule
    val intentsRule = IntentsRule()

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        SeriesInitializer::class.java,
        FavoriteInitializer::class.java,
        MainInitializer::class.java
    )

    @Test
    fun withSeriesFavorites_shouldShowFavorites() {
        seriesFavoritesFragment {
            withFavoritesSuccess()
        } check {
            favoritesDisplayed()
        }
    }

    @Test
    fun withSeriesFavoritesEmpty_shouldShowEmptyState() {
        seriesFavoritesFragment {
            withFavoritesEmpty()
        } check {
            emptyStateDisplayed()
        }
    }

    @Test
    fun withSeriesFavoritesError_shouldShowErrorState() {
        seriesFavoritesFragment {
            withFavoritesError()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withErrorState_andClickTryAgain_shouldLoadFavoritesAgain() {
        seriesFavoritesFragment {
            withFavoritesError()
        } launch {
            clickTryAgain()
        } check {
            favoritesLoaded(times = 2)
        }
    }

    @Test
    fun withSeriesFavorites_andClickFavorite_shouldOpenSeriesDetail() {
        seriesFavoritesFragment {
            withFavoritesSuccess()
        } launch {
            clickFavorite(position = 0)
        } check {
            seriesDetailOpened()
        }
    }

    @Test
    fun withSeriesFavorites_andClickSeeAll_shouldOpenFavoritesSeries() {
        seriesFavoritesFragment {
            withFavoritesSuccess()
        } launch {
            clickSeeAll()
        } check {
            seriesFavoritesOpened()
        }
    }

}