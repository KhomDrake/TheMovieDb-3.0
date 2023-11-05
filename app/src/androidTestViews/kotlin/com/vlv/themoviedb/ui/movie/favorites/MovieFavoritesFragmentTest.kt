package com.vlv.themoviedb.ui.movie.favorites

import com.vlv.favorite.FavoriteInitializer
import com.vlv.movie.MovieInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.data.network.database.TheMovieDbDao
import com.vlv.series.SeriesInitializer
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import com.vlv.themoviedb.ui.MainInitializer
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<TheMovieDbDao>(relaxed = true) }
}

class MovieFavoritesFragmentTest {

    @get:Rule
    val intentsRule = IntentsRule()

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java,
        SeriesInitializer::class.java,
        FavoriteInitializer::class.java,
        MainInitializer::class.java
    )

    @Test
    fun withMovieFavorites_shouldShowFavorites() {
        movieFavoritesFragment {
            withFavoritesSuccess()
        } check {
            favoritesDisplayed()
        }
    }

    @Test
    fun withMovieFavoritesEmpty_shouldShowEmptyState() {
        movieFavoritesFragment {
            withFavoritesEmpty()
        } check {
            emptyStateDisplayed()
        }
    }

    @Test
    fun withMovieFavoritesError_shouldShowErrorState() {
        movieFavoritesFragment {
            withFavoritesError()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withErrorState_andClickTryAgain_shouldLoadFavoritesAgain() {
        movieFavoritesFragment {
            withFavoritesError()
        } launch {
            clickTryAgain()
        } check {
            favoritesLoaded(times = 2)
        }
    }

    @Test
    fun withMovieFavorites_andClickFavorite_shouldOpenMovieDetail() {
        movieFavoritesFragment {
            withFavoritesSuccess()
        } launch {
            clickFavorite(position = 0)
        } check {
            movieDetailOpened()
        }
    }

    @Test
    fun withMovieFavorites_andClickSeeAll_shouldOpenFavoritesMovie() {
        movieFavoritesFragment {
            withFavoritesSuccess()
        } launch {
            clickSeeAll()
        } check {
            movieFavoritesOpened()
        }
    }

}