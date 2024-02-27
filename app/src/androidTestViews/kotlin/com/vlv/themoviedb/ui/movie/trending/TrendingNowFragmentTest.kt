package com.vlv.themoviedb.ui.movie.trending

import com.vlv.favorite.FavoriteInitializer
import com.vlv.movie.presentation.MovieInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.movie.data.repository.MovieRepository
import com.vlv.tv_show.SeriesInitializer
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

class TrendingNowFragmentTest {

    @get:Rule
    val intentsRule = IntentsRule()

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java,
        SeriesInitializer::class.java,
        FavoriteInitializer::class.java,
        MainInitializer::class.java
    )

    @Test
    fun withMoviesTrendingNowSuccess_shouldShowMovies() {
        trendingNowFragment {
            withTrendingNowSuccess()
        } check {
            moviesDisplayed()
        }
    }

    @Test
    fun withMoviesTrendingNowSuccess_andClickMovie_shouldOpenMovieDetail() {
        trendingNowFragment {
            withTrendingNowSuccess()
        } launch {
            clickMovie(position = 0)
        } check {
            movieDetailOpened()
        }
    }

    @Test
    fun withMoviesTrendingNowSuccess_andClickSeeAll_shouldOpenMovieTrendingNow() {
        trendingNowFragment {
            withTrendingNowSuccess()
        } launch {
            clickSeeAll()
        } check {
            movieTrendingNowOpened()
        }
    }

    @Test
    fun withMoviesTrendingNowEmpty_shouldShowEmptyState() {
        trendingNowFragment {
            withTrendingNowEmpty()
        } check {
            emptyStateDisplayed()
        }
    }

    @Test
    fun withMoviesTrendingNowFails_shouldShowErrorState() {
        trendingNowFragment {
            withTrendingNowFails()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withErrorState_andCLickTryAgain_shouldLoadMoviesTrendingNowAgain() {
        trendingNowFragment {
            withTrendingNowFails()
        } launch {
            clickTryAgain()
        } check {
            moviesTrendingNowLoaded(times = 2)
        }
    }

}