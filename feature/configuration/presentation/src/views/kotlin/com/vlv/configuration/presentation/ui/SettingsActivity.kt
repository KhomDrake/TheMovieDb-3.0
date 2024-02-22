package com.vlv.configuration.presentation.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.configuration.presentation.R
import com.vlv.configuration.domain.model.SettingsOption
import com.vlv.imperiya.core.ui.bottomsheet.SpinnerBottomSheet
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
        toolbar.navigationContentDescription = getString(com.vlv.ui.R.string.common_back_content_description)

        setupRecyclerView()
        loadConfig()

        viewModel.config.observe(this) {
            data {
                updateOptions()
            }
            error { e ->
            }
        }
    }

    private fun loadConfig() {
        viewModel.config().observe(this) {}
    }

    private fun updateOptions() {
        adapter.submitList(viewModel.options())
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
                viewModel.setConfigValue(item.name, settingsItem.id, item.value)
                items.announceForAccessibility(
                    getString(R.string.configuration_options_change_configuration)
                )
                updateOptions()
                dismiss()
            }
            setButtonText(context.getString(R.string.configuration_button))
        }.show(supportFragmentManager, SettingsActivity::class.java.name)
    }

    private fun onClickSwitch(settingsItem: SettingsItem, checkbox: Boolean) {
        viewModel.setConfigValue(settingsItem.id, checkbox)
        updateOptions()
    }

}