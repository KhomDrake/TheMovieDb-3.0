package com.vlv.themoviedb

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.configuration.domain.usecase.SetupConfigurationUseCase

class IntroViewModel(
    private val resources: Resources,
    private val useCase: SetupConfigurationUseCase
) : ViewModel() {

    fun loadConfig() = bondsmith<Unit>()
        .config {
            withCache(false)
            request {
                useCase.loadConfig(resources)
            }
        }
        .execute()
        .responseLiveData

}