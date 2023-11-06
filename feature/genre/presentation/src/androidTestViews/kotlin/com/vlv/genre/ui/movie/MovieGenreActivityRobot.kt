package com.vlv.genre.ui.movie

import android.content.res.Resources.NotFoundException
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.common.ui.route.toMovieGenre
import com.vlv.data.common.model.genre.GenresResponse
import com.vlv.data.common.model.movie.MoviesResponse
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.clickIgnoreConstraint
import com.vlv.test.loadObjectFromJson
import com.vlv.genre.R
import com.vlv.genre.data.api.DiscoverApi
import com.vlv.genre.data.api.GenresApi
import com.vlv.test.checkTextTabLayoutPosition
import com.vlv.test.hasText
import com.vlv.test.isDisplayed
import com.vlv.test.isNotDisplayed
import io.mockk.coEvery
import io.mockk.coVerify
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun MovieGenreActivityTest.movieGenreActivity(func: MovieGenreActivitySetup.() -> Unit) =
    MovieGenreActivitySetup().apply(func)

class MovieGenreActivitySetup :
    Setup<MovieGenreActivityLauncher, MovieGenreActivityCheck>, KoinComponent {

    private val discoverApi: DiscoverApi by inject()
    private val genreApi: GenresApi by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): MovieGenreActivityCheck {
        return MovieGenreActivityCheck()
    }

    override fun createLaunch(): MovieGenreActivityLauncher {
        return MovieGenreActivityLauncher()
    }

    override fun setupLaunch() {
        ActivityScenario.launch<MovieGenreActivity>(
            InstrumentationRegistry.getInstrumentation().context
                .toMovieGenre()
        )
    }

    fun withGenreSuccess() {
        val data = loadObjectFromJson<GenresResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_genres.json",
            moshi
        ) ?: return
        coEvery {
            genreApi.moviesGenres()
        } returns data
    }

    fun withGenreError() {
        coEvery {
            genreApi.moviesGenres()
        } throws NotFoundException()
    }

    fun withMoviesByGenreSuccess() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movies_by_genre.json",
            moshi
        ) ?: return

        coEvery {
            discoverApi.discoverMovieByGenres(any(), any())
        } returns data
    }

}

class MovieGenreActivityLauncher : Launch<MovieGenreActivityCheck> {

    override fun createCheck(): MovieGenreActivityCheck {
        return MovieGenreActivityCheck()
    }

    fun clickTryAgain() {
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }

}

class MovieGenreActivityCheck : Check, KoinComponent {

    private val genreApi: GenresApi by inject()

    fun genresDisplayed() {
        "Movies by genre".isDisplayed()

        R.id.tabs.isDisplayed()
        R.id.layout.isDisplayed()
        R.id.loading.isNotDisplayed()

        R.id.tabs.apply {
            checkTextTabLayoutPosition("Action", 0)
            checkTextTabLayoutPosition("Adventure", 1)
            checkTextTabLayoutPosition("Animation", 2)
            checkTextTabLayoutPosition("Comedy", 3)
        }
    }

    fun errorStateDisplayed() {
        R.id.tabs.isNotDisplayed()
        R.id.layout.isNotDisplayed()
        R.id.loading.isNotDisplayed()
        R.id.error.isDisplayed()

        com.vlv.imperiya.core.R.id.small_warning_title.hasText("Failed to load genres")
        com.vlv.imperiya.core.R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again")
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun genresLoaded(times: Int) {
        coVerify(exactly = times) {
            genreApi.moviesGenres()
        }
    }

}