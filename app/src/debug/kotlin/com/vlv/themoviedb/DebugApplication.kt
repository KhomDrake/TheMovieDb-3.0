package com.vlv.themoviedb

import br.com.mrocigno.bigbrother.core.BigBrother
import com.vlv.network.client.ContextProvider
import com.vlv.network.client.EnvConfig
import com.vlv.network.datastore.DataVault
import com.vlv.network.datastore.dataVaultScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class DebugApplication : TheMovieDb() {

    override fun onCreate() {
        super.onCreate()
        BigBrother.watch(this, isBubbleEnabled = true)
        registerActivityLifecycleCallbacks(ContextProvider)
        ContextProvider.context = WeakReference(this)
        dataVaultScope.launch {
            val previous = DataVault.getValueBoolean("ENV_MOCK") ?: false
            DataVault.setValue("ENV_MOCK", previous)
            EnvConfig.inMock = previous
        }
    }

}