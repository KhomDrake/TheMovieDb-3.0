package com.vlv.themoviedb.ui.menu

import com.vlv.data.network.NetworkInitializer
import com.vlv.favorite.FavoriteInitializer
import com.vlv.movie.presentation.MovieInitializer
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import com.vlv.themoviedb.ui.MainInitializer
import com.vlv.tv_show.SeriesInitializer
import org.junit.Rule
import org.junit.Test

class MenuFragmentTest {

    @get:Rule
    val intentsRule = IntentsRule()

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(),
        NetworkInitializer::class.java,
        MovieInitializer::class.java,
        SeriesInitializer::class.java,
        FavoriteInitializer::class.java,
        MainInitializer::class.java
    )

    @Test
    fun shouldShowMenuItems() {
        menuFragment {

        } check {
            menuItemsDisplayed()
        }
    }

    @Test
    fun whenClickInSettings_shouldOpenSettings() {
        menuFragment {

        } launch {
            clickSettings()
        } check {
            settingsOpened()
        }
    }

    @Test
    fun whenClickInMovieTrending_shouldOpenMovieTrending() {
        menuFragment {

        } launch {
            clickMovieTrending()
        } check {
            movieTrendingOpened()
        }
    }

    @Test
    fun whenClickInMovieTopRated_shouldOpenMovieTopRated() {
        menuFragment {

        } launch {
            clickMovieTopRated()
        } check {
            movieTopRatedOpened()
        }
    }

    @Test
    fun whenClickInMovieNowPlaying_shouldOpenMovieNowPlaying() {
        menuFragment {

        } launch {
            clickMovieNowPlaying()
        } check {
            movieNowPlayingOpened()
        }
    }

    @Test
    fun whenClickInMovieUpcoming_shouldOpenMovieUpcoming() {
        menuFragment {

        } launch {
            clickMovieUpcoming()
        } check {
            movieUpcomingOpened()
        }
    }

    @Test
    fun whenClickInMoviePopular_shouldOpenMoviePopular() {
        menuFragment {

        } launch {
            clickMoviePopular()
        } check {
            moviePopularOpened()
        }
    }

    @Test
    fun whenClickInMovieGenre_shouldOpenMovieGenre() {
        menuFragment {

        } launch {
            clickMovieGenre()
        } check {
            movieGenreOpened()
        }
    }

    @Test
    fun whenClickInMovieSearch_shouldOpenMovieSearch() {
        menuFragment {

        } launch {
            clickMovieSearch()
        } check {
            movieSearchOpened()
        }
    }

    @Test
    fun whenClickInSeriesTrending_shouldOpenSeriesTrending() {
        menuFragment {

        } launch {
            clickSeriesTrending()
        } check {
            seriesTrendingOpened()
        }
    }

    @Test
    fun whenClickInSeriesTopRated_shouldOpenSeriesTopRated() {
        menuFragment {

        } launch {
            clickSeriesTopRated()
        } check {
            seriesTopRatedOpened()
        }
    }

    @Test
    fun whenClickInSeriesAiringToday_shouldOpenSeriesAiringToday() {
        menuFragment {

        } launch {
            clickSeriesAiringToday()
        } check {
            seriesAiringTodayOpened()
        }
    }

    @Test
    fun whenClickInSeriesOnTheAir_shouldOpenSeriesOnTheAir() {
        menuFragment {

        } launch {
            clickSeriesOnTheAir()
        } check {
            seriesOnTheAirOpened()
        }
    }

    @Test
    fun whenClickInSeriesPopular_shouldOpenSeriesPopular() {
        menuFragment {

        } launch {
            clickSeriesPopular()
        } check {
            seriesPopularOpened()
        }
    }

    @Test
    fun whenClickInSeriesGenre_shouldOpenSeriesGenre() {
        menuFragment {

        } launch {
            clickSeriesGenre()
        } check {
            seriesGenreOpened()
        }
    }

    @Test
    fun whenClickInSeriesSearch_shouldOpenSeriesSearch() {
        menuFragment {

        } launch {
            clickSeriesSearch()
        } check {
            seriesSearchOpened()
        }
    }

    @Test
    fun whenClickInPeoplePopular_shouldOpenPeoplePopular() {
        menuFragment {

        } launch {
            clickPeoplePopular()
        } check {
            peoplePopularOpened()
        }
    }

    @Test
    fun whenClickInPeopleTrending_shouldOpenPeopleTrending() {
        menuFragment {

        } launch {
            clickPeopleTrending()
        } check {
            peopleTrendingOpened()
        }
    }

    @Test
    fun whenClickInPeopleSearch_shouldOpenPeopleSearch() {
        menuFragment {

        } launch {
            clickPeopleSearch()
        } check {
            peopleSearchOpened()
        }
    }

}