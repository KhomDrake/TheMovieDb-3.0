package com.vlv.network.client

import com.vlv.network.datastore.DataVault
import com.vlv.network.datastore.dataVaultScope
import kotlinx.coroutines.launch

object EnvConfig {

    var inMock: Boolean = false

    fun changeMock(newValue: Boolean = !inMock) {
        dataVaultScope.launch {
            DataVault.setValue("ENV_MOCK", newValue)
            inMock = newValue
        }
    }

}