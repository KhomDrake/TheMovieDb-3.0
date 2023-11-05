package com.vlv.movie.ui.detail.about

import android.content.res.Resources.NotFoundException
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.common.data.movie.Movie
import com.vlv.movie.R
import com.vlv.movie.ui.detail.cast.EXTRA_MOVIE
import com.vlv.data.network.model.movie.MovieDetailResponse
import com.vlv.data.network.repository.MovieDetailRepository
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.checkViewOnRecyclerViewPosition
import com.vlv.test.clickIgnoreConstraint
import com.vlv.test.isDisplayed
import com.vlv.test.isNotDisplayed
import com.vlv.test.loadObjectFromJson
import io.mockk.coEvery
import io.mockk.coVerify
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun AboutFragmentTest.aboutFragment(func: AboutFragmentSetup.() -> Unit) =
    AboutFragmentSetup().apply(func)

class AboutFragmentSetup : Setup<AboutFragmentLaunch, AboutFragmentCheck>, KoinComponent {

    private val repository: MovieDetailRepository by inject()
    private val moshi: Moshi by inject()

    private var args: Bundle? = null

    override fun createCheck(): AboutFragmentCheck {
        return AboutFragmentCheck()
    }

    override fun createLaunch(): AboutFragmentLaunch {
        return AboutFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<AboutFragment>(
            fragmentArgs = args,
            themeResId = com.vlv.imperiya.core.R.style.Imperiya_Theme
        )
    }

    fun withMovie() {
        args = bundleOf(
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
        args = null
    }

    fun withLoadMovieDetailSuccessful() {
        val data = loadObjectFromJson<MovieDetailResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_detail.json",
            moshi
        ) ?: return
        coEvery {
            repository.movieDetail(2)
        } returns data
    }

    fun withLoadMovieDetailFails() {
        coEvery {
            repository.movieDetail(2)
        } throws NotFoundException()
    }
}

class AboutFragmentLaunch : Launch<AboutFragmentCheck> {
    override fun createCheck(): AboutFragmentCheck {
        return AboutFragmentCheck()
    }

    fun clickTryAgain() {
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }

}

class AboutFragmentCheck : Check, KoinComponent {

    private val repository: MovieDetailRepository by inject()

    fun movieDetailInformationLoaded(times: Int) {
        coVerify(exactly = times) {
            repository.movieDetail(2)
        }
    }

    fun errorDisplayed() {
        R.id.warning_view_about.isDisplayed()
        R.id.about_content.isNotDisplayed()
    }

    fun dataDisplayed() {
        R.id.about_content.isDisplayed()
        R.id.warning_view_about.isNotDisplayed()
    }

    fun rightDataDisplayed() {
        R.id.about_content.apply {
            checkViewOnRecyclerViewPosition(
                0,
                childId = com.vlv.ui.R.id.big_text,
                viewMatcher = ViewMatchers.withText("Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.")
            )
            checkViewOnRecyclerViewPosition(
                1,
                childId = com.vlv.ui.R.id.about_item_title,
                viewMatcher = ViewMatchers.withText("Genres:")
            )
            checkViewOnRecyclerViewPosition(
                2,
                childId = com.vlv.ui.R.id.genres,
                viewMatcher = ViewMatchers.isDisplayed()
            )
        }
    }

}
