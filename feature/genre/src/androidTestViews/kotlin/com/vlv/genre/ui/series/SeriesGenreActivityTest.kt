package com.vlv.genre.ui.series

import com.vlv.genre.GenreInitialization
import com.vlv.genre.ui.movie.myModule
import com.vlv.network.NetworkInitializer
import com.vlv.test.KoinRule
import org.junit.Rule
import org.junit.Test

class SeriesGenreActivityTest {

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        GenreInitialization::class.java
    )

    @Test
    fun withGenresSuccess_shouldShowGenres() {
        seriesGenreActivity {
            withGenreSuccess()
            withSeriesByGenreSuccess()
        } check {
            genresDisplayed()
        }
    }

    @Test
    fun withGenreError_shouldShowErrorState() {
        seriesGenreActivity {
            withGenreError()
            withSeriesByGenreSuccess()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withErrorStateDisplayed_andClickTryAgain_shouldRequestGenreAgain() {
        seriesGenreActivity {
            withGenreError()
            withSeriesByGenreSuccess()
        } launch {
            clickTryAgain()
        } check {
            genresLoaded(times = 2)
        }
    }
    
}