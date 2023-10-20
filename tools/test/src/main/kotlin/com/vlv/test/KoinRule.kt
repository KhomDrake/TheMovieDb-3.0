package com.vlv.test

import androidx.startup.AppInitializer
import androidx.startup.Initializer
import androidx.test.core.app.ApplicationProvider
import com.jakewharton.threetenabp.AndroidThreeTen
import io.mockk.unmockkAll
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

class KoinRule(
    private val modules: List<Module>,
    private vararg val initializer: Class<out Initializer<Module>>,
    private val shouldStart: Boolean = true
) : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    kotlin.runCatching {
                        if(shouldStart) {
                            startKoin {
                                androidContext(ApplicationProvider.getApplicationContext())
                            }
                        }
                    }
                    AppInitializer.getInstance(ApplicationProvider.getApplicationContext())
                        .apply {
                            initializer.map { initializeComponent(it) }.let {
                                loadKoinModules(it + modules)
                            }
                        }

                    AndroidThreeTen.init(ApplicationProvider.getApplicationContext())
                    base.evaluate()
                } finally {
                    stopKoin()
                    unmockkAll()
                }
            }
        }
    }

}