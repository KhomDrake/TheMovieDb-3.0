package com.vlv.themoviedb.ui.menu

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.intent.matcher.BundleMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import com.vlv.common.data.movie.MovieListType
import com.vlv.common.data.tv_show.SeriesListType
import com.vlv.common.route.MOVIES_LISTING_TYPE_EXTRA
import com.vlv.common.route.SERIES_LISTING_TYPE_EXTRA
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.checkIntent
import com.vlv.test.checkViewOnRecyclerViewPosition
import com.vlv.test.clickOnRecyclerViewItem
import com.vlv.test.mockIntent
import com.vlv.test.scrollToPosition
import com.vlv.themoviedb.R

enum class MenuItems(val title: String, val isTitle: Boolean = false) {
    USER_TITLE("User", isTitle = true),
    SETTINGS("Settings"),
    MOVIES_TITLE("Movies", isTitle = true),
    MOVIES_TRENDING("Trending"),
    MOVIES_TOP_RATED("Top rated"),
    MOVIES_NOW_PLAYING("Now playing"),
    MOVIES_UPCOMING("Upcoming"),
    MOVIES_POPULAR("Popular"),
    MOVIES_GENRE("Genres"),
    MOVIES_SEARCH("Search"),
    SERIES_TITLE("Series", isTitle = true),
    SERIES_TRENDING_NOW("Trending"),
    SERIES_TOP_RATED("Top rated"),
    SERIES_AIRING_TODAY("Airing today"),
    SERIES_ON_THE_AIR("On the air"),
    SERIES_POPULAR("Popular"),
    SERIES_GENRE("Genres"),
    SERIES_SEARCH("Search"),
    PEOPLE_TITLE("People", isTitle = true),
    PEOPLE_POPULAR("Popular"),
    PEOPLE_TRENDING("Trending"),
    PEOPLE_SEARCH("Search")
}

fun MenuFragmentTest.menuFragment(func: MenuFragmentSetup.() -> Unit) =
    MenuFragmentSetup().apply(func)

class MenuFragmentSetup : Setup<MenuFragmentLaunch, MenuFragmentCheck> {
    override fun createCheck(): MenuFragmentCheck {
        return MenuFragmentCheck()
    }

    override fun createLaunch(): MenuFragmentLaunch {
        return MenuFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<MenuFragment>(
            themeResId = com.vlv.imperiya.core.R.style.Imperiya_Theme,
            fragmentArgs = null
        )
    }

}

class MenuFragmentLaunch : Launch<MenuFragmentCheck> {
    override fun createCheck(): MenuFragmentCheck {
        return MenuFragmentCheck()
    }

    fun clickSettings() {
        mockIntent("SETTINGS", targetContext = true)

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.SETTINGS.ordinal)
    }

    fun clickMovieTrending() {
        mockIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.TRENDING)),
        )

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.MOVIES_TRENDING.ordinal)
    }

    fun clickMovieTopRated() {
        mockIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.TOP_RATED)),
        )

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.MOVIES_TOP_RATED.ordinal)
    }

    fun clickMovieNowPlaying() {
        mockIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.NOW_PLAYING)),
        )

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.MOVIES_NOW_PLAYING.ordinal)
    }

    fun clickMovieUpcoming() {
        mockIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.UPCOMING)),
        )

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.MOVIES_UPCOMING.ordinal)
    }

    fun clickMoviePopular() {
        mockIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.POPULAR)),
        )

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.MOVIES_POPULAR.ordinal)
    }

    fun clickMovieGenre() {
        mockIntent("MOVIE_GENRE", targetContext = true)

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.MOVIES_GENRE.ordinal)
    }

    fun clickMovieSearch() {
        mockIntent("MOVIE_SEARCH", targetContext = true)

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.MOVIES_SEARCH.ordinal)
    }

    fun clickSeriesTrending() {
        mockIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(SERIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(SeriesListType.TRENDING)),
        )

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.SERIES_TRENDING_NOW.ordinal)
    }

    fun clickSeriesTopRated() {
        mockIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(SERIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(SeriesListType.TOP_RATED)),
        )

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.SERIES_TOP_RATED.ordinal)
    }

    fun clickSeriesAiringToday() {
        mockIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(SERIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(SeriesListType.AIRING_TODAY)),
        )

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.SERIES_AIRING_TODAY.ordinal)
    }

    fun clickSeriesOnTheAir() {
        mockIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(SERIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(SeriesListType.ON_THE_AIR)),
        )

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.SERIES_ON_THE_AIR.ordinal)
    }

    fun clickSeriesPopular() {
        mockIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(SERIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(SeriesListType.POPULAR)),
        )

        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.SERIES_POPULAR.ordinal)
    }

    fun clickSeriesGenre() {
        mockIntent("SERIES_GENRE", targetContext = true)
        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.SERIES_GENRE.ordinal)
    }

    fun clickSeriesSearch() {
        mockIntent("SERIES_SEARCH", targetContext = true)
        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.SERIES_SEARCH.ordinal)
    }

    fun clickPeoplePopular() {
        mockIntent("PEOPLE_POPULAR", targetContext = true)
        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.PEOPLE_POPULAR.ordinal)
    }

    fun clickPeopleTrending() {
        mockIntent("PEOPLE_TRENDING", targetContext = true)
        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.PEOPLE_TRENDING.ordinal)
    }

    fun clickPeopleSearch() {
        mockIntent("PEOPLE_SEARCH", targetContext = true)
        R.id.menu_items.clickOnRecyclerViewItem(position = MenuItems.PEOPLE_SEARCH.ordinal)
    }
}

class MenuFragmentCheck : Check {

    private fun Int.checkTitle(position: Int, text: String) {
        scrollToPosition(position)
        checkViewOnRecyclerViewPosition(
            position,
            ViewMatchers.withText(text),
            R.id.title_menu
        )
    }

    private fun Int.checkItem(position: Int, text: String) {
        scrollToPosition(position)
        checkViewOnRecyclerViewPosition(
            position,
            ViewMatchers.withText(text),
            R.id.menu_item_title
        )
    }

    fun menuItemsDisplayed() {
        R.id.menu_items.apply {
            MenuItems.values().forEach { item ->
                if(item.isTitle) {
                    checkTitle(item.ordinal, item.title)
                } else {
                    checkItem(item.ordinal, item.title)
                }
            }
        }
    }

    fun settingsOpened() {
        checkIntent("SETTINGS", targetContext = true)
    }

    fun movieTrendingOpened() {
        checkIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.TRENDING)),
        )
    }

    fun movieTopRatedOpened() {
        checkIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.TOP_RATED)),
        )
    }

    fun movieNowPlayingOpened() {
        checkIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.NOW_PLAYING)),
        )
    }

    fun movieUpcomingOpened() {
        checkIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.UPCOMING)),
        )
    }

    fun moviePopularOpened() {
        checkIntent(
            "MOVIE_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MOVIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MovieListType.POPULAR)),
        )
    }

    fun movieGenreOpened() {
        checkIntent("MOVIE_GENRE", targetContext = true)
    }

    fun movieSearchOpened() {
        mockIntent("MOVIE_SEARCH", targetContext = true)
    }

    fun seriesTrendingOpened() {
        checkIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(SERIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(SeriesListType.TRENDING)),
        )
    }

    fun seriesTopRatedOpened() {
        checkIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(SERIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(SeriesListType.TOP_RATED)),
        )
    }

    fun seriesAiringTodayOpened() {
        checkIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(SERIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(SeriesListType.AIRING_TODAY)),
        )
    }

    fun seriesOnTheAirOpened() {
        checkIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(SERIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(SeriesListType.ON_THE_AIR)),
        )
    }

    fun seriesPopularOpened() {
        checkIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(SERIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(SeriesListType.POPULAR)),
        )
    }

    fun seriesGenreOpened() {
        checkIntent("SERIES_GENRE", targetContext = true)
    }

    fun seriesSearchOpened() {
        checkIntent("SERIES_SEARCH", targetContext = true)
    }

    fun peoplePopularOpened() {
        checkIntent("PEOPLE_POPULAR", targetContext = true)
    }

    fun peopleTrendingOpened() {
        checkIntent("PEOPLE_TRENDING", targetContext = true)
    }

    fun peopleSearchOpened() {
        checkIntent("PEOPLE_SEARCH", targetContext = true)
    }

}