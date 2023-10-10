package com.vlv.configuration.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.configuration.R
import com.vlv.network.datastore.DataVault
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity(R.layout.configuration_settings_activity) {

    private val viewModel: SettingsViewModel by viewModel()

    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val items: RecyclerView by viewProvider(R.id.items)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        items.layoutManager = LinearLayoutManager(this)
        items.adapter = SettingsAdapter().apply {
            submitList(
                listOf(
                    SettingsItem(
                        "General",
                        SettingsItemType.TITLE,
                        "General",
                    ),
                    SettingsItem(
                        SettingsItemOption.ADULT_CONTENT.name,
                        SettingsItemType.SWITCH,
                        "Should show adult content"
                    ),
                    SettingsItem(
                        SettingsItemOption.LANGUAGE.name,
                        SettingsItemType.NORMAL,
                        "Languages",
                        "Language chosen: en-us"
                    ),
                    SettingsItem(
                        SettingsItemOption.REGION.name,
                        SettingsItemType.NORMAL,
                        "Region",
                        "Region chosen: en-us"
                    ),
                    SettingsItem(
                        "",
                        SettingsItemType.TITLE,
                        "Image Definition"
                    ),
                    SettingsItem(
                        SettingsItemOption.BACKDROP.name,
                        SettingsItemType.NORMAL,
                        "Backdrop definition",
                        "Definition chosen: ${DataVault.cachedData(SettingsItemOption.BACKDROP.name)}"
                    ),
                    SettingsItem(
                        SettingsItemOption.LOGO.name,
                        SettingsItemType.NORMAL,
                        "Logo definition",
                        "Definition chosen: ${DataVault.cachedData(SettingsItemOption.LOGO.name)}"
                    ),
                    SettingsItem(
                        SettingsItemOption.POSTER.name,
                        SettingsItemType.NORMAL,
                        "Poster definition",
                        "Definition chosen: ${DataVault.cachedData(SettingsItemOption.POSTER.name)}"
                    ),
                    SettingsItem(
                        SettingsItemOption.PROFILE.name,
                        SettingsItemType.NORMAL,
                        "Profile definition",
                        "Definition chosen: ${DataVault.cachedData(SettingsItemOption.PROFILE.name)}"
                    )
                )
            )
        }
    }

}