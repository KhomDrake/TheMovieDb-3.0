package com.vlv.themoviedb.ui.movie.trending

import android.content.res.Resources
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.intent.matcher.BundleMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.movie.MovieListType
import com.vlv.common.route.MOVIES_LISTING_TYPE_EXTRA
import com.vlv.data.common.model.movie.MoviesResponse
import com.vlv.movie.data.repository.MovieRepository
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.checkIntent
import com.vlv.test.checkViewOnRecyclerViewPosition
import com.vlv.test.clickIgnoreConstraint
import com.vlv.test.clickOnRecyclerViewItem
import com.vlv.test.hasText
import com.vlv.test.isDisplayed
import com.vlv.test.isNotDisplayed
import com.vlv.test.loadObjectFromJson
import com.vlv.test.mockIntent
import com.vlv.themoviedb.R
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun TrendingNowFragmentTest.trendingNowFragment(func: TrendingNowFragmentSetup.() -> Unit) =
    TrendingNowFragmentSetup().apply(func)

class TrendingNowFragmentSetup : Setup<TrendingNowFragmentLaunch, TrendingNowFragmentCheck>, KoinComponent {

    private val repository: MovieRepository by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): TrendingNowFragmentCheck {
        return TrendingNowFragmentCheck()
    }

    override fun createLaunch(): TrendingNowFragmentLaunch {
        return TrendingNowFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<TrendingNowFragment>(
            themeResId = R.style.Imperiya_Theme
        )
    }

    fun withTrendingNowSuccess() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movies_list.json",
            moshi
        ) ?: return

        every {
            repository.trendingMovies(any())
        } returns bondsmith<MoviesResponse>()
            .apply {
                setData(data)
            }
    }

    fun withTrendingNowEmpty() {
        coEvery {
            repository.trendingMovies(any())
        } returns bondsmith<MoviesResponse>()
            .apply {
                setData(
                    MoviesResponse(
                        1,
                        1,
                        1,
                        listOf()
                    )
                )
            }
    }

    fun withTrendingNowFails() {
        coEvery {
            repository.trendingMovies(any())
        } returns bondsmith<MoviesResponse>()
            .apply {
                setError(Resources.NotFoundException())
            }
    }

}

class TrendingNowFragmentLaunch : Launch<TrendingNowFragmentCheck> {
    override fun createCheck(): TrendingNowFragmentCheck {
        return TrendingNowFragmentCheck()
    }

    fun clickMovie(position: Int) {
        mockIntent("MOVIE_DETAIL", targetContext = true)
        R.id.movies.clickOnRecyclerViewItem(position)
    }

    fun clickSeeAll() {
        mockIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.TRENDING)),
        )

        R.id.see_all.clickIgnoreConstraint()
    }

    fun clickTryAgain() {
        R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }
}

class TrendingNowFragmentCheck : Check, KoinComponent {

    private val repository: MovieRepository by inject()

    fun moviesDisplayed() {
        R.id.list_title.hasText("Trending now")
        R.id.see_all.hasText("See All")
        R.id.indicator.isDisplayed()

        R.id.shimmer.isNotDisplayed()
        R.id.error_state.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()

        R.id.movies.apply {
            checkViewOnRecyclerViewPosition(
                0,
                ViewMatchers.withText("Expend4bles"),
                R.id.movie_title
            )
            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.withText("The Equalizer 3"),
                R.id.movie_title
            )
        }
    }

    fun movieDetailOpened() {
        checkIntent("MOVIE_DETAIL", targetContext = true)
    }

    fun movieTrendingNowOpened() {
        checkIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.TRENDING)),
        )
    }

    fun emptyStateDisplayed() {
        R.id.list_title.hasText("Trending now")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.movies.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.error_state.isNotDisplayed()

        R.id.empty_state.isDisplayed()

        R.id.title_state.hasText("None movie found")
    }

    fun errorStateDisplayed() {
        R.id.list_title.hasText("Trending now")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.movies.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()

        R.id.error_state.isDisplayed()
        R.id.small_warning_title.hasText("Failed to load trending movies")
        R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again button")
        R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun moviesTrendingNowLoaded(times: Int) {
        coVerify(exactly = times) {
            repository.trendingMovies(any())
        }
    }

}