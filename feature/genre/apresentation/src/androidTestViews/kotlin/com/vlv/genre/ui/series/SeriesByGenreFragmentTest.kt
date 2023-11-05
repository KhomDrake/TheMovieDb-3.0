package com.vlv.genre.ui.series

import com.vlv.genre.GenreInitialization
import com.vlv.genre.ui.movie.myModule
import com.vlv.data.network.NetworkInitializer
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import org.junit.Rule
import org.junit.Test

class SeriesByGenreFragmentTest {

    @get:Rule
    val intentsRule = IntentsRule()

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        GenreInitialization::class.java
    )

    @Test
    fun withGenreId_shouldRequestSeriesByGenres() {
        seriesByGenreFragment {
            withGenreId()
            withSeriesByGenreSuccess()
        } check {
            seriesByGenreLoaded(times = 1)
        }
    }

    @Test
    fun withoutGenreId_shouldShowErrorState() {
        seriesByGenreFragment {
            withoutGenreId()
            withSeriesByGenreSuccess()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withRequestSeriesByGenresSuccess_shouldShowSeries() {
        seriesByGenreFragment {
            withGenreId()
            withSeriesByGenreSuccess()
        } check {
            seriesDisplayed()
        }
    }

    @Test
    fun withRequestSeriesByGenresFails_shouldShowErrorState() {
        seriesByGenreFragment {
            withGenreId()
            withSeriesByGenreError()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withRequestSeriesByGenresEmpty_shouldShowEmptyState() {
        seriesByGenreFragment {
            withGenreId()
            withSeriesByGenreEmpty()
        } check {
            emptyStateDisplayed()
        }
    }

    @Test
    fun withErrorStateDisplayed_andClickTryAgain_shouldLoadSeriesByGenreAgain() {
        seriesByGenreFragment {
            withGenreId()
            withSeriesByGenreError()
        } launch {
            clickTryAgain()
        } check {
            seriesByGenreLoaded(times = 2)
        }
    }

    @Test
    fun withSeriesDisplayed_andClickSeries_shouldOpenSeriesDetail() {
        seriesByGenreFragment {
            withGenreId()
            withSeriesByGenreSuccess()
        } launch {
            clickSeries(position = 0)
        } check {
            seriesDetailOpened()
        }
    }
    
}