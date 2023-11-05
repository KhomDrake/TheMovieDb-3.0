package com.vlv.movie.ui.detail.recommendation

import android.content.res.Resources.NotFoundException
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.common.data.movie.Movie
import com.vlv.movie.ui.detail.cast.EXTRA_MOVIE
import com.vlv.data.network.api.MovieApi
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

fun RecommendationFragmentTest.recommendationFragment(func: RecommendationFragmentSetup.() -> Unit) =
    RecommendationFragmentSetup().apply(func)

class RecommendationFragmentSetup :
    Setup<RecommendationFragmentLaunch, RecommendationFragmentCheck>, KoinComponent {

    private val movieApi: MovieApi by inject()
    private val moshi: Moshi by inject()

    private var arguments = bundleOf()

    override fun createCheck(): RecommendationFragmentCheck {
        return RecommendationFragmentCheck()
    }

    override fun createLaunch(): RecommendationFragmentLaunch {
        return RecommendationFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<RecommendationFragment>(
            arguments,
            com.vlv.imperiya.core.R.style.Imperiya_Theme
        )
    }

    fun withMovie() {
        arguments = bundleOf(
            EXTRA_MOVIE to Movie(
                false,
                "/628Dep6AxEtDxjZoGP78TsOxYbK.jpg",
                "/628Dep6AxEtDxjZoGP78TsOxYbK.jpg",
                2,
                "Test Movie",
                "Test Movie Description"
            )
        )
    }

    fun withoutMovie() {
        arguments = bundleOf()
    }

    fun withRecommendationsSuccessful() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_recommendation.json",
            moshi
        ) ?: return

        coEvery {
            movieApi.movieRecommendations(
                2, 1
            )
        } returns data
    }

    fun withRecommendationsFails() {
        coEvery {
            movieApi.movieRecommendations(
                2, 1
            )
        } throws NotFoundException()
    }

    fun withRecommendationsEmpty() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_recommendation_empty.json",
            moshi
        ) ?: return

        coEvery {
            movieApi.movieRecommendations(
                2, 1
            )
        } returns data
    }

}

class RecommendationFragmentLaunch : Launch<RecommendationFragmentCheck> {
    override fun createCheck(): RecommendationFragmentCheck {
        return RecommendationFragmentCheck()
    }

    fun clickTryAgain() {
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }

    fun clickRecommendation(position: Int) {
        mockIntent("MOVIE_DETAIL")
        com.vlv.ui.R.id.listing_content.clickOnRecyclerViewItem(position)
    }

}

class RecommendationFragmentCheck : Check, KoinComponent {

    private val movieApi: MovieApi by inject()

    fun moviesDisplayed() {
        com.vlv.ui.R.id.empty_view_listing.isNotDisplayed()
        com.vlv.ui.R.id.warning_view_listing.isNotDisplayed()
        com.vlv.ui.R.id.shimmer_listing.isNotDisplayed()
        com.vlv.ui.R.id.listing_content.apply {
            isDisplayed()
            checkViewOnRecyclerViewPosition(
                0,
                ViewMatchers.withText("Doctor Strange in the Multiverse of Madness"),
                com.vlv.ui.R.id.movie_title
            )
            checkViewOnRecyclerViewPosition(
                0,
                ViewMatchers.isDisplayed(),
                com.vlv.ui.R.id.backdrop
            )
        }
    }

    fun emptyStateDisplayed() {
        com.vlv.ui.R.id.empty_view_listing.isDisplayed()
        com.vlv.ui.R.id.listing_content.isNotDisplayed()
        com.vlv.ui.R.id.warning_view_listing.isNotDisplayed()
        com.vlv.ui.R.id.shimmer_listing.isNotDisplayed()
    }

    fun errorDisplayed() {
        com.vlv.ui.R.id.empty_view_listing.isNotDisplayed()
        com.vlv.ui.R.id.listing_content.isNotDisplayed()
        com.vlv.ui.R.id.warning_view_listing.isDisplayed()
        com.vlv.ui.R.id.shimmer_listing.isNotDisplayed()
        com.vlv.imperiya.core.R.id.small_warning_title.hasText("Failed to load")
        com.vlv.imperiya.core.R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again")
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun recommendationLoaded(times: Int) {
        coVerify(exactly = times) {
            movieApi.movieRecommendations(
                2,
                1
            )
        }
    }

    fun movieDetailOpened() {
        checkIntent("MOVIE_DETAIL")
    }

}
