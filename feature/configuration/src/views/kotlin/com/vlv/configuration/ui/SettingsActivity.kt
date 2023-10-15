package com.vlv.configuration.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.configuration.R
import com.vlv.imperiya.ui.bottomsheet.SpinnerBottomSheet
import com.vlv.network.repository.SettingsOption
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity(R.layout.configuration_settings_activity) {

    private val viewModel: SettingsViewModel by viewModel()

    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val items: RecyclerView by viewProvider(R.id.items)

    private val adapter = SettingsAdapter(
        ::onClickItem,
        ::onClickSwitch
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        setupRecyclerView()
        loadConfig()

        viewModel.config.observe(this) {
            data {
                adapter.submitList(viewModel.options(this@SettingsActivity))
            }
            error { e ->
                Log.i("Vini", e.stackTraceToString())
            }
        }
    }

    private fun loadConfig() {
        viewModel.config().observe(this) {}
    }

    private fun setupRecyclerView() {
        items.layoutManager = LinearLayoutManager(this)
        items.adapter = adapter
    }

    private fun onClickItem(settingsItem: SettingsItem) {
        val data = viewModel.config.data ?: return

        val context: Context = this

        SpinnerBottomSheet().apply {
            when(settingsItem.id) {
                SettingsOption.POSTER.name -> {
                    setTitleText(context.getString(R.string.configuration_poster_title))
                    setItems(data.posterSizes)
                }
                SettingsOption.REGION.name -> {
                    setTitleText(context.getString(R.string.configuration_country_title))
                    setItems(data.countries)
                }
                SettingsOption.LANGUAGE.name -> {
                    setTitleText(context.getString(R.string.configuration_language_title))
                    setItems(data.languages)
                }
                SettingsOption.PROFILE.name -> {
                    setTitleText(context.getString(R.string.configuration_profile_title))
                    setItems(data.profileSizes)
                }
                SettingsOption.BACKDROP.name -> {
                    setTitleText(context.getString(R.string.configuration_backdrop_title))
                    setItems(data.backdropSizes)
                }
                SettingsOption.LOGO.name -> {
                    setTitleText(context.getString(R.string.configuration_logo_title))
                    setItems(data.logoSizes)
                }
                else -> Unit
            }

            setOnClickConfirm {
                val item = it ?: return@setOnClickConfirm
                viewModel.setConfigValue(settingsItem.id, item.value)
                loadConfig()
                dismiss()
            }
            setButtonText(context.getString(R.string.configuration_button))
        }.show(supportFragmentManager, SettingsActivity::class.java.name)
    }

    private fun onClickSwitch(settingsItem: SettingsItem, checkbox: Boolean) {
        viewModel.setConfigValue(settingsItem.id, checkbox)
        loadConfig()
    }

}