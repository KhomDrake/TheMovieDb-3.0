package com.vlv.movie.ui.detail

import com.vlv.movie.MovieInitializer
import com.vlv.network.NetworkInitializer
import com.vlv.network.repository.ConfigurationRepository
import com.vlv.network.repository.FavoriteRepository
import com.vlv.network.repository.GenreRepository
import com.vlv.network.repository.MovieDetailRepository
import com.vlv.network.repository.MovieRepository
import com.vlv.network.repository.PeopleDetailRepository
import com.vlv.network.repository.PeopleRepository
import com.vlv.network.repository.SearchRepository
import com.vlv.network.repository.SeriesDetailRepository
import com.vlv.network.repository.SeriesRepository
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<FavoriteRepository>(relaxed = true) }
    single { mockk<MovieDetailRepository>(relaxed = true) }
    single { mockk<ConfigurationRepository>(relaxed = true) }
    single { mockk<SearchRepository>(relaxed = true) }
    single { mockk<SeriesRepository>(relaxed = true) }
    single { mockk<MovieRepository>(relaxed = true) }
    single { mockk<SeriesDetailRepository>(relaxed = true) }
    single { mockk<PeopleRepository>(relaxed = true) }
    single { mockk<PeopleDetailRepository>(relaxed = true) }
    single { mockk<GenreRepository>(relaxed = true) }
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