package com.vlv.movie.ui.detail

import android.content.Intent
import android.content.res.Resources.NotFoundException
import androidx.paging.PagingData
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.movie.toDetailObject
import com.vlv.common.data.movie.toFavorite
import com.vlv.common.ui.route.toMovieDetail
import com.vlv.data.common.model.credit.CreditsResponse
import com.vlv.data.common.model.movie.MovieDetailResponse
import com.vlv.data.common.model.movie.MoviesResponse
import com.vlv.data.common.model.review.ReviewsResponse
import com.vlv.favorite.data.FavoriteRepository
import com.vlv.movie.data.repository.MovieDetailRepository
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.clickIgnoreConstraint
import com.vlv.test.hasText
import com.vlv.test.isDisplayed
import com.vlv.test.isNotDisplayed
import com.vlv.test.loadObjectFromJson
import com.vlv.test.withDrawable
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun MovieDetailActivityTest.movieDetailActivity(func: MovieDetailActivitySetup.() -> Unit) =
    MovieDetailActivitySetup().apply(func)

class MovieDetailActivitySetup : Setup<MovieDetailActivityLaunch, MovieDetailActivityCheck>, KoinComponent {

    private val moshi: Moshi by inject()

    private lateinit var intent: Intent

    private val repository: MovieDetailRepository by inject()
    private val favoriteRepository: FavoriteRepository by inject()

    override fun createCheck(): MovieDetailActivityCheck {
        return MovieDetailActivityCheck()
    }

    override fun createLaunch(): MovieDetailActivityLaunch {
        return MovieDetailActivityLaunch()
    }

    private fun defaultConfig() {
        coEvery {
            favoriteRepository.addFavorite(
                any()
            )
        } returns Unit

        coEvery {
            favoriteRepository.removeFavorite(
                any()
            )
        } returns Unit

        val data = loadObjectFromJson<CreditsResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_credit.json",
            moshi
        ) ?: return

        coEvery {
            repository.movieCast(2)
        } returns data

        val dataRecommendation = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_recommendation.json",
            moshi
        ) ?: return

        coEvery {
            repository.movieRecommendations(
                any(), any()
            )
        } returns flow {
            emit(PagingData.empty())
        }

        val dataReview = loadObjectFromJson<ReviewsResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_reviews.json",
            moshi
        ) ?: return

        coEvery {
            repository.movieReviews(2)
        } returns dataReview
    }

    override fun setupLaunch() {
        defaultConfig()
        ActivityScenario.launch<MovieDetailActivity>(intent)
    }

    fun withMovieData() {
        intent = InstrumentationRegistry.getInstrumentation().context
            .toMovieDetail(
                Movie(
                    false,
                    "/628Dep6AxEtDxjZoGP78TsOxYbK.jpg",
                    "/628Dep6AxEtDxjZoGP78TsOxYbK.jpg",
                    2,
                    "Test Movie",
                    "Test Movie Description"
                ).toDetailObject()
            )
    }

    fun withMovieDetailSuccess() {
        val data = loadObjectFromJson<MovieDetailResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_detail.json",
            moshi
        ) ?: return
        coEvery {
            repository.movieDetail(2)
        } returns data
    }

    fun withMovieDetailFails() {
        coEvery {
            repository.movieDetail(2)
        } throws NotFoundException()
    }

    fun withMovieFavorite() {
        coEvery {
            favoriteRepository.getFavorite(2)
        } returns Movie(
            false,
            "/628Dep6AxEtDxjZoGP78TsOxYbK.jpg",
            "/628Dep6AxEtDxjZoGP78TsOxYbK.jpg",
            2,
            "Test Movie",
            "Test Movie Description"
        ).toFavorite()
    }

    fun withMovieNotFavorite() {
        coEvery {
            favoriteRepository.getFavorite(2)
        } returns null
    }
}

class MovieDetailActivityLaunch : Launch<MovieDetailActivityCheck> {
    override fun createCheck(): MovieDetailActivityCheck {
        return MovieDetailActivityCheck()
    }

    fun clickFavoriteIcon() {
        com.vlv.ui.R.id.heart.clickIgnoreConstraint()
    }

}

class MovieDetailActivityCheck : Check, KoinComponent {

    private val favoriteRepository: FavoriteRepository by inject()

    fun movieNormalInformationDisplayed() {
        com.vlv.ui.R.id.expanded_title.hasText("Test Movie")
        com.vlv.ui.R.id.score.isNotDisplayed()
        com.vlv.ui.R.id.expanded_date_and_time.isNotDisplayed()
    }

    fun movieTotalInformationDisplayed() {
        com.vlv.ui.R.id.expanded_title.hasText("Test Movie")
        com.vlv.ui.R.id.score.apply {
            isDisplayed()
            hasText("8.0")
        }
        com.vlv.ui.R.id.expanded_date_and_time.apply {
            isDisplayed()
            hasText("Dec/2021 - 2h 29min")
        }
    }

    fun iconFavoriteDisplayed() {
        com.vlv.ui.R.id.heart.withDrawable(
            com.vlv.imperiya.core.R.drawable.ic_heart_filled
        )
    }

    fun iconNotFavoriteDisplayed() {
        com.vlv.ui.R.id.heart.withDrawable(
            com.vlv.imperiya.core.R.drawable.ic_heart_enable
        )
    }

    fun favoriteMovieCalled() {
        coVerify {
            favoriteRepository.addFavorite(any())
        }
    }

    fun undoFavoriteMovieCalled() {
        coVerify {
            favoriteRepository.removeFavorite(any())
        }
    }

}