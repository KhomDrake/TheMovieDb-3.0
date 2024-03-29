package com.vlv.movie.ui.detail.about

import androidx.test.core.app.ApplicationProvider
import com.vlv.data.local.datastore.DataVault
import com.vlv.movie.presentation.MovieInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.favorite.domain.FavoriteDomainInitializer
import com.vlv.movie.data.MovieDataInitializer
import com.vlv.movie.data.repository.MovieDetailRepository
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<MovieDetailRepository>(relaxed = true) }
}

class AboutFragmentTest {

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java,
        MovieDataInitializer::class.java,
        FavoriteDomainInitializer::class.java
    )

    @Before
    fun setup() {
        DataVault.init(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun whenMovieIsSet_shouldLoadMovieDetailInformation() {
        aboutFragment {
            withMovie()
            withLoadMovieDetailSuccessful()
        } check {
            movieDetailInformationLoaded(times = 1)
        }
    }

    @Test
    fun whenMovieIsNotSet_shouldShowErrorScreen() {
        aboutFragment {
            withoutMovie()
        } check {
            errorDisplayed()
        }
    }

    @Test
    fun whenLoadMovieDetailIsSuccessful_shouldShowData() {
        aboutFragment {
            withMovie()
            withLoadMovieDetailSuccessful()
        } check {
            dataDisplayed()
        }
    }

    @Test
    fun whenLoadMovieDetailIsSuccessful_shouldShowRightDataInfo() {
        aboutFragment {
            withMovie()
            withLoadMovieDetailSuccessful()
        } check {
            rightDataDisplayed()
        }
    }

    @Test
    fun whenLoadMovieDetailFails_shouldShowError() {
        aboutFragment {
            withMovie()
            withLoadMovieDetailFails()
        } check {
            errorDisplayed()
        }
    }

    @Test
    fun whenLoadMovieDetailFailsAndShowingError_andClickTryAgain_shouldLoadMovieAgain() {
        aboutFragment {
            withMovie()
            withLoadMovieDetailFails()
        } launch {
            clickTryAgain()
        } check {
            movieDetailInformationLoaded(times = 2)
        }
    }

}