package com.vlv.movie.ui.detail.cast

import com.vlv.movie.MovieInitializer
import com.vlv.network.NetworkInitializer
import com.vlv.network.repository.MovieDetailRepository
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<MovieDetailRepository>(relaxed = true) }
}

class MovieCastFragmentTest {

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
    fun withSettingMovie_shouldLoadCastInformation() {
        movieCastFragment {
            withMovie()
            castInformationSuccessful()
        } check {
            castInformationLoaded(times = 1)
        }
    }

    @Test
    fun withoutSettingMovie_shouldErrorState() {
        movieCastFragment {
            withoutMovie()
            castInformationSuccessful()
        } check {
            castInformationLoaded(times = 0)
            errorStateDisplayed()
        }
    }

    @Test
    fun whenLoadCastInformationFails_shouldErrorState() {
        movieCastFragment {
            withMovie()
            castInformationFailure()
        } check {
            castInformationLoaded(times = 1)
            errorStateDisplayed()
        }
    }

    @Test
    fun whenShowErrorState_andClickTryAgain_shouldLoadCastAgain() {
        movieCastFragment {
            withMovie()
            castInformationFailure()
        } launch {
            clickTryAgain()
        } check {
            castInformationLoaded(times = 3)
        }
    }

    @Test
    fun whenLoadCastInformationSucceed_shouldCastInformation() {
        movieCastFragment {
            withMovie()
            castInformationSuccessful()
        } check {
            castInformationDisplayed()
        }
    }

    @Test
    fun whenLoadCastInformationSucceed_andClickCastItem_shouldOpenCastDetail() {
        movieCastFragment {
            withMovie()
            castInformationSuccessful()
        } launch {
            clickCast(position = 1)
        } check {
            castDetailOpened()
        }
    }

}