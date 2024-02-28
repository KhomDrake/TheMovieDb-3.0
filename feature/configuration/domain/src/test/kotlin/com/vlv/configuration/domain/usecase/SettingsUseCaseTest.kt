package com.vlv.configuration.domain.usecase

import com.vlv.configuration.data.ConfigurationRepository
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SettingsUseCaseTest {

    @Test
    fun asdas() = runTest {
        val repository = mockk<ConfigurationRepository>(relaxed = true)
        val useCase = SettingsUseCase(repository)
    }

}