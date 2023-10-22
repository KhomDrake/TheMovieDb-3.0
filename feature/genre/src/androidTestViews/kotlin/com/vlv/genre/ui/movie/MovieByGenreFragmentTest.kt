package com.vlv.genre.ui.movie

import com.vlv.genre.GenreInitialization
import com.vlv.network.NetworkInitializer
import com.vlv.network.api.DiscoverApi
import com.vlv.network.api.GenresApi
import com.vlv.network.repository.GenreRepository
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

val myModule = module {
    single { mockk<GenresApi>(relaxed = true) }
    single { mockk<DiscoverApi>(relaxed = true) }
    single { GenreRepository(get(), get()) }
}

class MovieByGenreFragmentTest {

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
    fun withGenreId_shouldRequestMovieByGenres() {
        movieByGenreFragment {
            withGenreId()
            withMovieByGenresSuccess()
        } check {
            moviesByGenresLoaded(times = 1)
        }
    }

    @Test
    fun withoutGenreId_shouldShowErrorState() {
        movieByGenreFragment {
            withoutGenreId()
            withMovieByGenresSuccess()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withRequestMovieByGenresSuccess_shouldShowMovies() {
        movieByGenreFragment {
            withGenreId()
            withMovieByGenresSuccess()
        } check {
            moviesDisplayed()
        }
    }

    @Test
    fun withRequestMovieByGenresFails_shouldShowErrorState() {
        movieByGenreFragment {
            withGenreId()
            withMovieByGenresError()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withRequestMovieByGenresEmpty_shouldShowEmptyState() {
        movieByGenreFragment {
            withGenreId()
            withMovieByGenresEmpty()
        } check {
            emptyStateDisplayed()
        }
    }

    @Test
    fun withErrorStateDisplayed_andClickTryAgain_shouldLoadMovieByGenreAgain() {
        movieByGenreFragment {
            withGenreId()
            withMovieByGenresError()
        } launch {
            clickTryAgain()
        } check {
            moviesByGenresLoaded(times = 2)
        }
    }

    @Test
    fun withMovies_andClickMovie_shouldGoToMovieDetail() {
        movieByGenreFragment {
            withGenreId()
            withMovieByGenresSuccess()
        } launch {
            clickMovie(position = 0)
        } check {
            movieDetailOpened()
        }
    }

}