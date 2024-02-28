package com.vlv.libraries.sample

import android.content.Context
import androidx.startup.Initializer
import com.vlv.libraries.sample.bondsmith.BondsmithRepository
import com.vlv.libraries.sample.bondsmith.BondsmithViewModel
import com.vlv.util.ModuleInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import timber.log.Timber

class SampleInitialization : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                single { BondsmithRepository() }
                viewModel { BondsmithViewModel(get()) }
            }
        )

    override val shouldStartKoin: Boolean
        get() = true

    override fun create(context: Context): Module {
        Timber.plant(Timber.DebugTree())
        return super.create(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}