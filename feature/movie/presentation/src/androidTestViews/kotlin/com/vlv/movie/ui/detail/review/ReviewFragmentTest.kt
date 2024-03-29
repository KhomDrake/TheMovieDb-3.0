package com.vlv.movie.ui.detail.review

import com.vlv.movie.presentation.MovieInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.movie.data.repository.MovieDetailRepository
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<MovieDetailRepository>(relaxed = true) }
}

class ReviewFragmentTest {

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java
    )

    @Test
    fun withMovie_shouldLoadReviews() {
        reviewFragment {
            withMovie()
            withMoviesReviewsSuccessful()
        } check {
            reviewsLoaded(times = 1)
        }
    }

    @Test
    fun withoutMovie_shouldShowErrorState() {
        reviewFragment {
            withoutMovie()
            withMoviesReviewsSuccessful()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withMovieAndLoadReviewsSuccessful_shouldShowReviews() {
        reviewFragment {
            withMovie()
            withMoviesReviewsSuccessful()
        } check {
            reviewsDisplayed()
        }
    }

    @Test
    fun withMovieAndLoadReviewsEmpty_shouldShowStateView() {
        reviewFragment {
            withMovie()
            withMoviesReviewsEmpty()
        } check {
            emptyStateDisplayed()
        }
    }

    @Test
    fun withMovieAndLoadReviewsFails_shouldErrorState() {
        reviewFragment {
            withMovie()
            withMoviesReviewsFailed()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun whenShowingErrorState_andClickTryAgain_shouldLoadReviewsAgain() {
        reviewFragment {
            withMovie()
            withMoviesReviewsFailed()
        } launch {
            clickTryAgain()
        } check {
            reviewsLoaded(times = 2)
        }
    }

}