package com.vlv.themoviedb

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.network.repository.ConfigurationRepository

class IntroViewModel(
    private val repository: ConfigurationRepository
) : ViewModel() {

    fun loadConfig() = bondsmith<Unit>()
        .request {
            repository.loadConfig()
            repository.setupDataVault()
        }
        .execute()
        .responseLiveData

}