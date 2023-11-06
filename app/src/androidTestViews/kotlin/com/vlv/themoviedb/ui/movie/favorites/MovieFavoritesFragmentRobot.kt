package com.vlv.themoviedb.ui.movie.favorites

import android.content.res.Resources.NotFoundException
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.intent.matcher.BundleMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.movie.toFavorite
import com.vlv.common.ui.route.FAVORITE_TYPE_EXTRA
import com.vlv.data.common.model.movie.MoviesResponse
import com.vlv.data.network.database.data.FavoriteType
import com.vlv.favorite.domain.usecase.MovieFavoriteUseCase
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

fun MovieFavoritesFragmentTest.movieFavoritesFragment(func: MovieFavoritesFragmentSetup.() -> Unit) =
    MovieFavoritesFragmentSetup().apply(func)

class MovieFavoritesFragmentSetup :
    Setup<MovieFavoritesFragmentLaunch, MovieFavoritesFragmentCheck>, KoinComponent {

    private val useCase: MovieFavoriteUseCase by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): MovieFavoritesFragmentCheck {
        return MovieFavoritesFragmentCheck()
    }

    override fun createLaunch(): MovieFavoritesFragmentLaunch {
        return MovieFavoritesFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<MovieFavoritesFragment>(
            themeResId = com.vlv.imperiya.core.R.style.Imperiya_Theme
        )
    }

    fun withFavoritesSuccess() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movies_list.json",
            moshi
        ) ?: return

        coEvery {
            useCase.favorites()
        } returns data.movies.map { Movie(it).toFavorite() }
    }

    fun withFavoritesError() {
        coEvery {
            useCase.favorites()
        } throws NotFoundException()
    }

    fun withFavoritesEmpty() {
        coEvery {
            useCase.favorites()
        } returns listOf()
    }

}

class MovieFavoritesFragmentLaunch : Launch<MovieFavoritesFragmentCheck> {
    override fun createCheck(): MovieFavoritesFragmentCheck {
        return MovieFavoritesFragmentCheck()
    }

    fun clickTryAgain() {
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }

    fun clickFavorite(position: Int) {
        mockIntent("MOVIE_DETAIL", targetContext = true)
        R.id.movies.clickOnRecyclerViewItem(position)
    }

    fun clickSeeAll() {
        mockIntent(
            "FAVORITES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(FAVORITE_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(FavoriteType.MOVIE)),
        )

        R.id.see_all.clickIgnoreConstraint()
    }
}

class MovieFavoritesFragmentCheck : Check, KoinComponent {

    private val useCase: MovieFavoriteUseCase by inject()

    fun favoritesDisplayed() {
        R.id.list_title.hasText("Favorites")
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

    fun emptyStateDisplayed() {
        R.id.list_title.hasText("Favorites")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.movies.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.error_state.isNotDisplayed()

        R.id.empty_state.isDisplayed()

        com.vlv.imperiya.core.R.id.title_state.hasText("None favorite movie found")
    }

    fun errorStateDisplayed() {
        R.id.list_title.hasText("Favorites")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.movies.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()

        R.id.error_state.isDisplayed()
        com.vlv.imperiya.core.R.id.small_warning_title.hasText("Failed to load favorites movies")
        com.vlv.imperiya.core.R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again button")
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun favoritesLoaded(times: Int) {
        coVerify(exactly = times) {
            useCase.favorites()
        }
    }

    fun movieDetailOpened() {
        checkIntent("MOVIE_DETAIL", targetContext = true)
    }

    fun movieFavoritesOpened() {
        checkIntent(
            "FAVORITES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(FAVORITE_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(FavoriteType.MOVIE)),
        )
    }

}