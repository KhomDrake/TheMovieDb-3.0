package com.vlv.movie.ui.detail.recommendation

import com.vlv.movie.presentation.MovieInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.movie.data.api.MovieApi
import com.vlv.movie.data.repository.MovieDetailRepository
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<MovieApi>(relaxed = true) }
    single { MovieDetailRepository(get()) }
}

class RecommendationFragmentTest {

    @get:Rule
    val intentsRule = IntentsRule()

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java
    )

    @Test
    fun whenRecommendationsLoadedSuccessful_shouldShowMovies() {
        recommendationFragment {
            withMovie()
            withRecommendationsSuccessful()
        } check {
            moviesDisplayed()
        }
    }

    @Test
    fun whenShowingRecommendation_andClickOne_shouldOpenMovieDetails() {
        recommendationFragment {
            withMovie()
            withRecommendationsSuccessful()
        } launch {
            clickRecommendation(position = 1)
        } check {
            movieDetailOpened()
        }
    }

    @Test
    fun whenRecommendationsIsEmpty_shouldShowEmptyState() {
        recommendationFragment {
            withMovie()
            withRecommendationsEmpty()
        } check {
            emptyStateDisplayed()
        }
    }

    @Test
    fun whenRecommendationsLoadedFails_shouldShowErrorState() {
        recommendationFragment {
            withMovie()
            withRecommendationsFails()
        } check {
            errorDisplayed()
        }
    }

    @Test
    fun withMovieSet_shouldLoadRecommendation() {
        recommendationFragment {
            withMovie()
            withRecommendationsSuccessful()
        } check {
            recommendationLoaded(times = 1)
        }
    }

    @Test
    fun withoutMovieSet_shouldErrorState() {
        recommendationFragment {
            withoutMovie()
            withRecommendationsSuccessful()
        } check {
            errorDisplayed()
        }
    }

    @Test
    fun whenIsShowingErrorState_andClickTryAgain_shouldLoadRecommendationAgain() {
        recommendationFragment {
            withMovie()
            withRecommendationsFails()
        } launch {
            clickTryAgain()
        } check {
            recommendationLoaded(times = 2)
        }
    }

}