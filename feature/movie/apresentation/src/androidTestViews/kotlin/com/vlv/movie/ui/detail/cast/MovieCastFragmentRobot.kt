package com.vlv.movie.ui.detail.cast

import android.content.res.Resources.NotFoundException
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.common.data.movie.Movie
import com.vlv.data.network.model.credit.CreditsResponse
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.checkIntent
import com.vlv.test.checkViewOnRecyclerViewPosition
import com.vlv.test.clickIgnoreConstraint
import com.vlv.test.clickOnRecyclerViewItem
import com.vlv.test.hasText
import com.vlv.test.isDisplayed
import com.vlv.test.loadObjectFromJson
import com.vlv.test.mockIntent
import io.mockk.coEvery
import io.mockk.coVerify
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun MovieCastFragmentTest.movieCastFragment(func: MovieCastFragmentSetup.() -> Unit) =
    MovieCastFragmentSetup().apply(func)

class MovieCastFragmentSetup : Setup<MovieCastFragmentLaunch, MovieCastFragmentCheck>, KoinComponent {

    private val repository: MovieDetailRepository by inject()
    private val moshi: Moshi by inject()

    private var arguments = bundleOf()

    override fun createCheck(): MovieCastFragmentCheck {
        return MovieCastFragmentCheck()
    }

    override fun createLaunch(): MovieCastFragmentLaunch {
        return MovieCastFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<MovieCastFragment>(
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

    fun castInformationSuccessful() {
        val data = loadObjectFromJson<CreditsResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_credit.json",
            moshi
        ) ?: return

        coEvery {
            repository.movieCast(2)
        } returns data
    }

    fun castInformationFailure() {
        coEvery {
            repository.movieCast(2)
        } throws NotFoundException()
    }

}

class MovieCastFragmentLaunch : Launch<MovieCastFragmentCheck> {

    override fun createCheck(): MovieCastFragmentCheck {
        return MovieCastFragmentCheck()
    }

    fun clickTryAgain() {
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }

    fun clickCast(position: Int) {
        mockIntent("PEOPLE_DETAIL")
        com.vlv.ui.R.id.cast_content.clickOnRecyclerViewItem(position)
    }

}

class MovieCastFragmentCheck : Check, KoinComponent {

    private val repository: MovieDetailRepository by inject()

    fun castInformationLoaded(times: Int) {
        Thread.sleep(100)
        coVerify(exactly = times) {
            repository.movieCast(2)
        }
    }

    fun errorStateDisplayed() {
        com.vlv.ui.R.id.warning_view_cast.isDisplayed()
        com.vlv.imperiya.core.R.id.small_warning_title.hasText("Failed to load cast")
        com.vlv.imperiya.core.R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again")
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun castInformationDisplayed() {
        com.vlv.ui.R.id.cast_content.apply {
            isDisplayed()
            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.withText("Tom Holland"),
                com.vlv.ui.R.id.person_name
            )
            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.withText("Peter Parker / Spider-Man"),
                com.vlv.ui.R.id.character
            )
        }
    }

    fun castDetailOpened() {
        checkIntent("PEOPLE_DETAIL")
    }
}