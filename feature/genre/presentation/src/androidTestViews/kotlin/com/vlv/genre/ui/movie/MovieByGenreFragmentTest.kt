package com.vlv.genre.ui.movie

import com.vlv.data.network.NetworkInitializer
import com.vlv.genre.GenreInitialization
import com.vlv.genre.data.GenreDataInitializer
import com.vlv.genre.data.GenreRepository
import com.vlv.genre.data.api.DiscoverApi
import com.vlv.genre.data.api.GenresApi
import com.vlv.genre.domain.GenreDomainInitializer
import com.vlv.genre.domain.usecase.MovieGenreUseCase
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

val myModule = module {
    single { mockk<DiscoverApi>(relaxed = true) }
    single { mockk<GenresApi>(relaxed = true) }
    single { GenreRepository(get(), get()) }
    single { MovieGenreUseCase(get()) }
}

class MovieByGenreFragmentTest {

    @get:Rule
    val intentsRule = IntentsRule()

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