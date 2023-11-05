package com.vlv.movie.ui.listing

import com.vlv.movie.MovieInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.data.network.api.MovieApi
import com.vlv.data.network.repository.MovieRepository
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<MovieApi>(relaxed = true) }
    single { MovieRepository(get()) }
}

class ListingMovieActivityTest {

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java
    )

    @Test
    fun whenLoadMovies_shouldShowMovies() {
        listingMovie {
            trending()
            successLoading()
        } check {
            dataDisplayed()
        }
    }

    @Test
    fun whenLoadMoviesTrending_shouldLoadMoviesTrending() {
        listingMovie {
            trending()
            successLoading()
        } check {
            trendingLoaded()
        }
    }

    @Test
    fun whenLoadMoviesUpcoming_shouldLoadMoviesUpcoming() {
        listingMovie {
            upcoming()
            successLoading()
        } check {
            upcomingLoaded()
        }
    }

    @Test
    fun whenLoadMoviesPopular_shouldLoadMoviesPopular() {
        listingMovie {
            popular()
            successLoading()
        } check {
            popularLoaded()
        }
    }

    @Test
    fun whenLoadMoviesTopRated_shouldLoadMoviesTopRated() {
        listingMovie {
            topRated()
            successLoading()
        } check {
            topRatedLoaded()
        }
    }

    @Test
    fun whenLoadMoviesNowPlaying_shouldLoadMoviesNowPlaying() {
        listingMovie {
            nowPlaying()
            successLoading()
        } check {
            nowPlayingLoaded()
        }
    }

    @Test
    fun whenLoadMoviesFailed_shouldShowErrorState() {
        listingMovie {
            trending()
            errorLoading()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun whenShowErrorState_andClickTryAgain_shouldLoadDataAgain() {
        listingMovie {
            trending()
            errorLoading()
        } launch {
            clickTryAgain()
        } check {
            loadTrendingMovie(times = 2)
        }
    }

}