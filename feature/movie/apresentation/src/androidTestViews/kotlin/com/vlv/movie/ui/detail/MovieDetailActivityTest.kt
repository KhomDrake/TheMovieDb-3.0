package com.vlv.movie.ui.detail

import com.vlv.data.network.NetworkInitializer
import com.vlv.favorite.data.FavoriteRepository
import com.vlv.movie.MovieInitializer
import com.vlv.movie.data.repository.MovieDetailRepository
import com.vlv.movie.data.repository.MovieRepository
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<FavoriteRepository>(relaxed = true) }
    single { mockk<MovieDetailRepository>(relaxed = true) }
    single { mockk<MovieRepository>(relaxed = true) }
}

@Ignore("All tests are running forever, without reason")
class MovieDetailActivityTest {

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java
    )

    @Test
    fun withObjectData_shouldMovieTitle() {
        movieDetailActivity {
            withMovieData()
            withMovieDetailFails()
            withMovieFavorite()
        } check {
            movieNormalInformationDisplayed()
        }
    }

    @Test
    fun withObjectDataAndMovieDetailSuccess_shouldMovieTotalInformation() {
        movieDetailActivity {
            withMovieData()
            withMovieDetailSuccess()
            withMovieFavorite()
        } check {
            movieTotalInformationDisplayed()
        }
    }

    @Test
    fun withObjectDataAndMovieDetailFails_shouldMovieNormalInformation() {
        movieDetailActivity {
            withMovieData()
            withMovieDetailFails()
            withMovieFavorite()
        } check {
            movieNormalInformationDisplayed()
        }
    }

    @Test
    fun withMovieFavorite_shouldShowIconFavorite() {
        movieDetailActivity {
            withMovieData()
            withMovieFavorite()
            withMovieDetailSuccess()
        } check {
            iconFavoriteDisplayed()
        }
    }

    @Test
    fun withMovieNotFavorite_shouldShowIconNotFavorite() {
        movieDetailActivity {
            withMovieData()
            withMovieDetailSuccess()
            withMovieNotFavorite()
        } check {
            iconNotFavoriteDisplayed()
        }
    }

    @Test
    fun withMovieNotFavorite_andClickFavoriteIcon_shouldFavoriteMovie() {
        movieDetailActivity {
            withMovieData()
            withMovieNotFavorite()
            withMovieDetailSuccess()
        } launch {
            clickFavoriteIcon()
        } check {
            favoriteMovieCalled()
        }
    }

    @Test
    fun withMovieFavorite_andClickFavoriteIcon_shouldUndoFavoriteMovie() {
        movieDetailActivity {
            withMovieData()
            withMovieFavorite()
            withMovieDetailSuccess()
        } launch {
            clickFavoriteIcon()
        } check {
            undoFavoriteMovieCalled()
        }
    }

}