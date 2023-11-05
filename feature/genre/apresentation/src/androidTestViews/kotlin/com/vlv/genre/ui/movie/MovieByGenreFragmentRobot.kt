package com.vlv.genre.ui.movie

import android.content.res.Resources.NotFoundException
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.genre.R
import com.vlv.data.network.model.movie.MoviesResponse
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
import io.mockk.coEvery
import io.mockk.coVerify
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun MovieByGenreFragmentTest.movieByGenreFragment(func: MovieByGenreFragmentSetup.() -> Unit) =
    MovieByGenreFragmentSetup().apply(func)

class MovieByGenreFragmentSetup :
    Setup<MovieByGenreFragmentLaunch, MovieByGenreFragmentCheck>, KoinComponent {

    private val discoverApi: DiscoverApi by inject()
    private val moshi: Moshi by inject()

    private var arguments = bundleOf()

    override fun createCheck(): MovieByGenreFragmentCheck {
        return MovieByGenreFragmentCheck()
    }

    override fun createLaunch(): MovieByGenreFragmentLaunch {
        return MovieByGenreFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<MovieByGenreFragment>(
            fragmentArgs = arguments,
            themeResId = com.vlv.imperiya.core.R.style.Imperiya_Theme
        )
    }

    fun withGenreId() {
        arguments = bundleOf(
            GENRE_ID_EXTRA to 42
        )
    }

    fun withoutGenreId() {
        arguments = bundleOf()
    }

    fun withMovieByGenresSuccess() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movies_by_genre.json",
            moshi
        ) ?: return

        coEvery {
            discoverApi.discoverMovieByGenres(
                42,
                1
            )
        } returns data
    }

    fun withMovieByGenresError() {
        coEvery {
            discoverApi.discoverMovieByGenres(
                42,
                1
            )
        } throws NotFoundException("asda", null)
    }

    fun withMovieByGenresEmpty() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movies_by_genre_empty.json",
            moshi
        ) ?: return

        coEvery {
            discoverApi.discoverMovieByGenres(
                42,
                1
            )
        } returns data
    }
}

class MovieByGenreFragmentLaunch : Launch<MovieByGenreFragmentCheck> {
    override fun createCheck(): MovieByGenreFragmentCheck {
        return MovieByGenreFragmentCheck()
    }

    fun clickTryAgain() {
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }

    fun clickMovie(position: Int) {
        mockIntent("MOVIE_DETAIL")
        R.id.items.clickOnRecyclerViewItem(position)
    }
}

class MovieByGenreFragmentCheck : Check, KoinComponent {

    private val discoverApi: DiscoverApi by inject()

    fun moviesByGenresLoaded(times: Int) {
        coVerify(exactly = times) {
            discoverApi.discoverMovieByGenres(
                42,
                1
            )
        }
    }

    fun errorStateDisplayed() {
        R.id.error.isDisplayed()
        R.id.items.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()

        com.vlv.imperiya.core.R.id.small_warning_title.hasText("Failed to load")
        com.vlv.imperiya.core.R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again")
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun moviesDisplayed() {
        R.id.error.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()

        R.id.items.apply {
            isDisplayed()
            checkViewOnRecyclerViewPosition(
                0,
                ViewMatchers.withText("Expend4bles"),
                com.vlv.ui.R.id.movie_title
            )
        }
    }

    fun emptyStateDisplayed() {
        R.id.error.isNotDisplayed()
        R.id.items.isNotDisplayed()
        R.id.empty_state.isDisplayed()
        R.id.shimmer.isNotDisplayed()

        com.vlv.imperiya.core.R.id.title_state.hasText("No movie found")
    }

    fun movieDetailOpened() {
        checkIntent("MOVIE_DETAIL")
    }

}