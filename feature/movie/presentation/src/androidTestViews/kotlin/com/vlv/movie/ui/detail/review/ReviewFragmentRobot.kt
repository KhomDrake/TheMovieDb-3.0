package com.vlv.movie.ui.detail.review

import android.content.res.Resources.NotFoundException
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.movie.Movie
import com.vlv.data.common.model.review.ReviewsResponse
import com.vlv.movie.data.repository.MovieDetailRepository
import com.vlv.movie.presentation.ui.detail.cast.EXTRA_MOVIE
import com.vlv.movie.presentation.ui.detail.review.MovieReviewFragment
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.checkViewOnRecyclerViewPosition
import com.vlv.test.clickIgnoreConstraint
import com.vlv.test.hasText
import com.vlv.test.isDisplayed
import com.vlv.test.isNotDisplayed
import com.vlv.test.loadObjectFromJson
import io.mockk.coVerify
import io.mockk.every
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun ReviewFragmentTest.reviewFragment(func: ReviewFragmentSetup.() -> Unit) =
    ReviewFragmentSetup().apply(func)

class ReviewFragmentSetup : Setup<ReviewFragmentLaunch, ReviewFragmentCheck>, KoinComponent {

    private var arguments = bundleOf()

    private val repository: MovieDetailRepository by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): ReviewFragmentCheck {
        return ReviewFragmentCheck()
    }

    override fun createLaunch(): ReviewFragmentLaunch {
        return ReviewFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<MovieReviewFragment>(
            fragmentArgs = arguments,
            themeResId = R.style.Imperiya_Theme
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

    fun withMoviesReviewsSuccessful() {
        val data = loadObjectFromJson<ReviewsResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_reviews.json",
            moshi
        ) ?: return

        every {
            repository.movieReviews(2)
        } returns bondsmith<ReviewsResponse>()
            .apply {
                setData(data)
            }
    }

    fun withMoviesReviewsFailed() {
        every {
            repository.movieReviews(2)
        } returns bondsmith<ReviewsResponse>()
            .apply {
                setError(NotFoundException())
            }
    }

    fun withMoviesReviewsEmpty() {
        val data = loadObjectFromJson<ReviewsResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_reviews_empty.json",
            moshi
        ) ?: return

        every {
            repository.movieReviews(2)
        } returns bondsmith<ReviewsResponse>()
            .apply {
                setData(data)
            }
    }

}

class ReviewFragmentLaunch : Launch<ReviewFragmentCheck> {
    override fun createCheck(): ReviewFragmentCheck {
        return ReviewFragmentCheck()
    }

    fun clickTryAgain() {
        R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }

}

class ReviewFragmentCheck : Check, KoinComponent {

    private val repository: MovieDetailRepository by inject()

    fun reviewsLoaded(times: Int) {
        Thread.sleep(100)
        coVerify(exactly = times) {
            repository.movieReviews(2)
        }
    }

    fun errorStateDisplayed() {
        com.vlv.ui.R.id.warning_view_review.isDisplayed()
        com.vlv.ui.R.id.review.isNotDisplayed()
        com.vlv.ui.R.id.empty_view_review.isNotDisplayed()
        com.vlv.ui.R.id.shimmer_review.isNotDisplayed()

        R.id.small_warning_title.hasText("Failed to load reviews")
        R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again")
        R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun reviewsDisplayed() {
        com.vlv.ui.R.id.warning_view_review.isNotDisplayed()
        com.vlv.ui.R.id.empty_view_review.isNotDisplayed()
        com.vlv.ui.R.id.shimmer_review.isNotDisplayed()

        com.vlv.ui.R.id.review.apply {
            isDisplayed()
            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.withText("garethmb"),
                com.vlv.ui.R.id.person_name
            )
        }
    }

    fun emptyStateDisplayed() {
        com.vlv.ui.R.id.warning_view_review.isNotDisplayed()
        com.vlv.ui.R.id.review.isNotDisplayed()
        com.vlv.ui.R.id.shimmer_review.isNotDisplayed()
        com.vlv.ui.R.id.empty_view_review.isDisplayed()
        R.id.title_state.hasText("None review found")
    }

}
