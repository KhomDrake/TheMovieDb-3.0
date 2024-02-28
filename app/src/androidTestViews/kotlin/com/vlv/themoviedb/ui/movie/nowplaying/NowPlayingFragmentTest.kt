package com.vlv.themoviedb.ui.movie.nowplaying

import com.vlv.favorite.FavoriteInitializer
import com.vlv.movie.presentation.MovieInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.movie.data.repository.MovieRepository
import com.vlv.tv_show.TvShowInitializer
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import com.vlv.themoviedb.ui.MainInitializer
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<MovieRepository>(relaxed = true) }
}

class NowPlayingFragmentTest {

    @get:Rule
    val intentsRule = IntentsRule()

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java,
        TvShowInitializer::class.java,
        FavoriteInitializer::class.java,
        MainInitializer::class.java
    )

    @Test
    fun withMoviesNowPlayingSuccess_shouldShowMovies() {
        nowPlayingFragment {
            withNowPlayingSuccess()
        } check {
            moviesDisplayed()
        }
    }

    @Test
    fun withMoviesNowPlayingSuccess_andClickMovie_shouldOpenMovieDetail() {
        nowPlayingFragment {
            withNowPlayingSuccess()
        } launch {
            clickMovie(position = 0)
        } check {
            movieDetailOpened()
        }
    }

    @Test
    fun withMoviesNowPlayingSuccess_andClickSeeAll_shouldOpenMovieNowPlaying() {
        nowPlayingFragment {
            withNowPlayingSuccess()
        } launch {
            clickSeeAll()
        } check {
            movieNowPlayingOpened()
        }
    }

    @Test
    fun withMoviesNowPlayingEmpty_shouldShowEmptyState() {
        nowPlayingFragment {
            withNowPlayingEmpty()
        } check {
            emptyStateDisplayed()
        }
    }

    @Test
    fun withMoviesNowPlayingFails_shouldShowErrorState() {
        nowPlayingFragment {
            withNowPlayingFails()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withErrorState_andCLickTryAgain_shouldLoadMoviesNowPlayingAgain() {
        nowPlayingFragment {
            withNowPlayingFails()
        } launch {
            clickTryAgain()
        } check {
            moviesNowPlayingLoaded(times = 2)
        }
    }

}