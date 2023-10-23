package com.vlv.themoviedb.ui

import com.vlv.favorite.FavoriteInitializer
import com.vlv.movie.MovieInitializer
import com.vlv.network.NetworkInitializer
import com.vlv.network.database.TheMovieDbDao
import com.vlv.network.repository.MovieRepository
import com.vlv.network.repository.SearchRepository
import com.vlv.network.repository.SeriesRepository
import com.vlv.search.SearchInitializer
import com.vlv.series.SeriesInitializer
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<SeriesRepository>(relaxed = true) }
    single { mockk<MovieRepository>(relaxed = true) }
    single { mockk<TheMovieDbDao>(relaxed = true) }
    single { mockk<SearchRepository>(relaxed = true) }
}
class MainActivityTest {

    @get:Rule
    val intentsRule = IntentsRule()

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java,
        SearchInitializer::class.java,
        SeriesInitializer::class.java,
        FavoriteInitializer::class.java,
        MainInitializer::class.java
    )

    @Test
    fun inTheStart_shouldDisplayedMovies() {
        mainActivity {
            withMovies()
        } check {
            moviesDisplayed()
        }
    }

    @Test
    fun whenClickInSeries_shouldOpenSeriesAndLoadSeries() {
        mainActivity {
            withMovies()
            withSeries()
        } launch {
            clickSeriesBottomNavigation()
        } check {
            seriesDisplayed()
        }
    }

    @Test
    fun whenClickInSearch_shouldOpenSearch() {
        mainActivity {
            withMovies()
            withSearch()
        } launch {
            clickSearchBottomNavigation()
        } check {
            searchDisplayed()
        }
    }

    @Test
    fun whenClickInFavorites_shouldOpenFavorites() {
        mainActivity {
            withMovies()
            withFavorites()
        } launch {
            clickFavoritesBottomNavigation()
        } check {
            favoritesDisplayed()
        }
    }

    @Test
    fun whenClickInMenu_shouldOpenMenu() {
        mainActivity {
            withMovies()
        } launch {
            clickMenuBottomNavigation()
        } check {
            menuDisplayed()
        }
    }

}