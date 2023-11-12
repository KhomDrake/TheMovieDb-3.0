package com.vlv.themoviedb.ui.movie.nowplaying

import android.content.res.Resources.NotFoundException
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.intent.matcher.BundleMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun NowPlayingFragmentTest.nowPlayingFragment(func: NowPlayingFragmentSetup.() -> Unit) =
    NowPlayingFragmentSetup().apply(func)

class NowPlayingFragmentSetup : Setup<NowPlayingFragmentLaunch, NowPlayingFragmentCheck>, KoinComponent {

    private val repository: MovieRepository by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): NowPlayingFragmentCheck {
        return NowPlayingFragmentCheck()
    }

    override fun createLaunch(): NowPlayingFragmentLaunch {
        return NowPlayingFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<NowPlayingFragment>(
            themeResId = com.vlv.imperiya.core.R.style.Imperiya_Theme
        )
    }

    fun withNowPlayingSuccess() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movies_list.json",
            moshi
        ) ?: return

        coEvery {
            repository.nowPlaying()
        } returns data
    }

    fun withNowPlayingEmpty() {
        coEvery {
            repository.nowPlaying()
        } returns MoviesResponse(
            1,
            1,
            1,
            listOf()
        )
    }

    fun withNowPlayingFails() {
        coEvery {
            repository.nowPlaying()
        } throws NotFoundException()
    }

}

class NowPlayingFragmentLaunch : Launch<NowPlayingFragmentCheck> {
    override fun createCheck(): NowPlayingFragmentCheck {
        return NowPlayingFragmentCheck()
    }

    fun clickTryAgain() {
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.clickIgnoreConstraint()
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
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.NOW_PLAYING)),
        )

        R.id.see_all.clickIgnoreConstraint()
    }
}

class NowPlayingFragmentCheck : Check, KoinComponent {

    private val repository: MovieRepository by inject()

    fun moviesDisplayed() {
        R.id.list_title.hasText("Playing now")
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

    fun errorStateDisplayed() {
        R.id.list_title.hasText("Playing now")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.movies.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()

        R.id.error_state.isDisplayed()
        com.vlv.imperiya.core.R.id.small_warning_title.hasText("Failed to load movies")
        com.vlv.imperiya.core.R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again button")
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun emptyStateDisplayed() {
        R.id.list_title.hasText("Playing now")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.movies.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.error_state.isNotDisplayed()

        R.id.empty_state.isDisplayed()

        com.vlv.imperiya.core.R.id.title_state.hasText("None movie found")
    }

    fun moviesNowPlayingLoaded(times: Int) {
        coVerify(exactly = times) {
            repository.nowPlaying()
        }
    }

    fun movieDetailOpened() {
        checkIntent("MOVIE_DETAIL", targetContext = true)
    }

    fun movieNowPlayingOpened() {
        checkIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.NOW_PLAYING)),
        )
    }

}