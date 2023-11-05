package com.vlv.movie.ui.listing

import android.content.Intent
import android.content.res.Resources.NotFoundException
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.common.ui.route.toMovieNowPlaying
import com.vlv.common.ui.route.toMoviePopular
import com.vlv.common.ui.route.toMovieTopRated
import com.vlv.common.ui.route.toMovieTrending
import com.vlv.common.ui.route.toMovieUpcoming
import com.vlv.data.network.api.MovieApi
import com.vlv.data.network.model.movie.MoviesResponse
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.clickIgnoreConstraint
import com.vlv.test.isDisplayed
import com.vlv.test.loadObjectFromJson
import io.mockk.coEvery
import io.mockk.coVerify
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.vlv.imperiya.core.R as ImperiyaR

fun ListingMovieActivityTest.listingMovie(func: ListingMovieActivitySetup.() -> Unit) =
    ListingMovieActivitySetup().apply(func)

class ListingMovieActivitySetup : Setup<ListingMovieActivityLaunch, ListingMovieActivityCheck>, KoinComponent {

    private val movieApi: MovieApi by inject()
    private val moshi: Moshi by inject()

    private var intent = Intent(
        InstrumentationRegistry.getInstrumentation().context,
        ListingMovieActivity::class.java
    )

    override fun createCheck(): ListingMovieActivityCheck {
        return ListingMovieActivityCheck()
    }

    override fun createLaunch(): ListingMovieActivityLaunch {
        return ListingMovieActivityLaunch()
    }

    override fun setupLaunch() {
        ActivityScenario.launch<ListingMovieActivity>(intent)
    }

    fun trending() {
        intent = InstrumentationRegistry.getInstrumentation().context.toMovieTrending()
        val context = InstrumentationRegistry.getInstrumentation().context
        val data = loadObjectFromJson<MoviesResponse>(
            context,
            "listing_movies.json",
            moshi
        ) ?: return
        coEvery {
            movieApi.trending(any(), 1)
        } returns data
    }

    fun upcoming() {
        intent = InstrumentationRegistry.getInstrumentation().context.toMovieUpcoming()
        val context = InstrumentationRegistry.getInstrumentation().context
        val data = loadObjectFromJson<MoviesResponse>(
            context,
            "listing_movies.json",
            moshi
        ) ?: return
        coEvery {
            movieApi.upcoming(1)
        } returns data
    }

    fun popular() {
        intent = InstrumentationRegistry.getInstrumentation().context.toMoviePopular()
        val context = InstrumentationRegistry.getInstrumentation().context
        val data = loadObjectFromJson<MoviesResponse>(
            context,
            "listing_movies.json",
            moshi
        ) ?: return
        coEvery {
            movieApi.popular(1)
        } returns data
    }

    fun topRated() {
        intent = InstrumentationRegistry.getInstrumentation().context.toMovieTopRated()
        val context = InstrumentationRegistry.getInstrumentation().context
        val data = loadObjectFromJson<MoviesResponse>(
            context,
            "listing_movies.json",
            moshi
        ) ?: return
        coEvery {
            movieApi.topRated(1)
        } returns data
    }

    fun nowPlaying() {
        intent = InstrumentationRegistry.getInstrumentation().context.toMovieNowPlaying()
        val context = InstrumentationRegistry.getInstrumentation().context
        val data = loadObjectFromJson<MoviesResponse>(
            context,
            "listing_movies.json",
            moshi
        ) ?: return
        coEvery {
            movieApi.nowPlaying(1)
        } returns data
    }

    fun successLoading() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val data = loadObjectFromJson<MoviesResponse>(
            context,
            "listing_movies.json",
            moshi
        ) ?: return
        coEvery {
            movieApi.trending(any(), 1)
        } returns data
    }

    fun errorLoading() {
        coEvery {
            movieApi.trending(any(), 1)
        } throws NotFoundException()
    }

}

class ListingMovieActivityLaunch : Launch<ListingMovieActivityCheck> {

    override fun createCheck(): ListingMovieActivityCheck {
        return ListingMovieActivityCheck()
    }

    fun clickTryAgain() {
        ImperiyaR.id.small_warning_try_again_button.clickIgnoreConstraint()
    }

}

class ListingMovieActivityCheck : Check, KoinComponent {

    private val movieApi: MovieApi by inject()
    
    fun loadTrendingMovie(times: Int) {
        coVerify(exactly = times) {
            movieApi.trending(any(), 1)
        }
    }

    fun errorStateDisplayed() {
        
    }

    fun nowPlayingLoaded() {
        coVerify {
            movieApi.nowPlaying(1)
        }
    }

    fun popularLoaded() {
        coVerify {
            movieApi.popular(1)
        }
    }

    fun upcomingLoaded() {
        coVerify {
            movieApi.upcoming(1)
        }
    }

    fun topRatedLoaded() {
        coVerify {
            movieApi.topRated(1)
        }
    }

    fun trendingLoaded() {
        coVerify {
            movieApi.trending(any(), 1)
        }
    }

    fun dataDisplayed() {
        com.vlv.ui.R.id.items.isDisplayed()
    }
}