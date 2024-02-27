package com.vlv.themoviedb.ui.tv_show.favorites

import android.content.res.Resources.NotFoundException
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.intent.matcher.BundleMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.data.tv_show.toFavorite
import com.vlv.common.route.FAVORITE_TYPE_EXTRA
import com.vlv.data.common.model.tvshow.TvShowsResponse
import com.vlv.data.database.data.Favorite
import com.vlv.data.database.data.ItemType
import com.vlv.favorite.domain.usecase.TvShowFavoriteUseCase
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
import io.mockk.coVerify
import io.mockk.every
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun SeriesFavoritesFragmentTest.seriesFavoritesFragment(func: SeriesFavoritesFragmentSetup.() -> Unit) =
    SeriesFavoritesFragmentSetup().apply(func)

class SeriesFavoritesFragmentSetup
    : Setup<SeriesFavoritesFragmentLaunch, SeriesFavoritesFragmentCheck>, KoinComponent {

    private val useCase: TvShowFavoriteUseCase by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): SeriesFavoritesFragmentCheck {
        return SeriesFavoritesFragmentCheck()
    }

    override fun createLaunch(): SeriesFavoritesFragmentLaunch {
        return SeriesFavoritesFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<SeriesFavoritesFragment>(
            themeResId = com.vlv.imperiya.core.R.style.Imperiya_Theme
        )
    }

    fun withFavoritesSuccess() {
        val data = loadObjectFromJson<TvShowsResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_list.json",
            moshi
        ) ?: return

        every {
            useCase.favorites()
        } returns bondsmith<List<Favorite>>()
            .apply {
                setData(
                    data.tvShows.map { TvShow(it).toFavorite() }
                )
            }
    }

    fun withFavoritesEmpty() {
        every {
            useCase.favorites()
        } returns bondsmith<List<Favorite>>()
            .apply {
                setData(
                    listOf()
                )
            }
    }

    fun withFavoritesError() {
        every {
            useCase.favorites()
        } returns bondsmith<List<Favorite>>()
            .apply {
                setError(NotFoundException())
            }
    }
}

class SeriesFavoritesFragmentLaunch : Launch<SeriesFavoritesFragmentCheck> {

    override fun createCheck(): SeriesFavoritesFragmentCheck {
        return SeriesFavoritesFragmentCheck()
    }

    fun clickTryAgain() {
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }

    fun clickFavorite(position: Int) {
        mockIntent("SERIES_DETAIL", targetContext = true)
        R.id.series.clickOnRecyclerViewItem(position)
    }

    fun clickSeeAll() {
        mockIntent(
            "FAVORITES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(FAVORITE_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(ItemType.TV_SHOW)),
        )

        R.id.see_all.clickIgnoreConstraint()
    }

}

class SeriesFavoritesFragmentCheck : Check, KoinComponent {

    private val useCase: TvShowFavoriteUseCase by inject()

    fun favoritesDisplayed() {
        R.id.list_title.hasText("Favorites")
        R.id.see_all.hasText("See All")
        R.id.indicator.isDisplayed()

        R.id.shimmer.isNotDisplayed()
        R.id.error_state.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()

        R.id.series.apply {
            checkViewOnRecyclerViewPosition(
                0,
                ViewMatchers.withText("Tagesschau"),
                R.id.series_title
            )
            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.withText("Elas por Elas"),
                R.id.series_title
            )
        }
    }

    fun emptyStateDisplayed() {
        R.id.list_title.hasText("Favorites")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.series.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.error_state.isNotDisplayed()

        R.id.empty_state.isDisplayed()

        com.vlv.imperiya.core.R.id.title_state.hasText("None favorite tv shows found")
    }

    fun errorStateDisplayed() {
        R.id.list_title.hasText("Favorites")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.series.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()

        R.id.error_state.isDisplayed()
        com.vlv.imperiya.core.R.id.small_warning_title.hasText("Failed to load favorites tv shows")
        com.vlv.imperiya.core.R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again button")
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun favoritesLoaded(times: Int) {
        coVerify(exactly = times) {
            useCase.favorites()
        }
    }

    fun seriesDetailOpened() {
        checkIntent("SERIES_DETAIL", targetContext = true)
    }

    fun seriesFavoritesOpened() {
        checkIntent(
            "FAVORITES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(FAVORITE_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(ItemType.TV_SHOW)),
        )
    }

}