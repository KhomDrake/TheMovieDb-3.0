package com.vlv.themoviedb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.startup.AppInitializer
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.network.NetworkInitializer
import com.vlv.network.client.EnvConfig
import com.vlv.network.di.Initializers
import com.vlv.network.di.ModuleInitializer
import org.koin.core.context.stopKoin

class EnvActivity : AppCompatActivity(R.layout.env_activity) {

    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val save: AppCompatButton by viewProvider(R.id.save)
    private val inMock: SwitchCompat by viewProvider(R.id.in_mock)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        inMock.isChecked = EnvConfig.inMock

        save.setOnClickListener {
            EnvConfig.changeMock(newValue = inMock.isChecked)
            stopKoin()
            val hashMap = hashMapOf<String, String>()
            Initializers.initializers.forEach { initializer ->
                initializer.dependencies().forEach { dependency ->
                    if(!hashMap.containsKey(dependency.name)) {
                        hashMap.set(dependency.name, "")
                        AppInitializer.getInstance(this)
                            .initializeComponent(dependency::class.java)
                    }
                }
            }
        }
    }

}