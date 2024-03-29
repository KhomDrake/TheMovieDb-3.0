package com.vlv.genre.ui.movie

import com.vlv.genre.GenreInitialization
import com.vlv.data.network.NetworkInitializer
import com.vlv.genre.data.GenreDataInitializer
import com.vlv.genre.domain.GenreDomainInitializer
import com.vlv.test.KoinRule
import org.junit.Rule
import org.junit.Test

class MovieGenreActivityTest {

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        GenreDataInitializer::class.java,
        GenreDomainInitializer::class.java,
        GenreInitialization::class.java
    )

    @Test
    fun withGenresSuccess_shouldShowGenres() {
        movieGenreActivity {
            withGenreSuccess()
            withMoviesByGenreSuccess()
        } check {
            genresDisplayed()
        }
    }

    @Test
    fun withGenreError_shouldShowErrorState() {
        movieGenreActivity {
            withGenreError()
            withMoviesByGenreSuccess()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withErrorStateDisplayed_andClickTryAgain_shouldRequestGenreAgain() {
        movieGenreActivity {
            withGenreError()
            withMoviesByGenreSuccess()
        } launch {
            clickTryAgain()
        } check {
            genresLoaded(times = 2)
        }
    }

}