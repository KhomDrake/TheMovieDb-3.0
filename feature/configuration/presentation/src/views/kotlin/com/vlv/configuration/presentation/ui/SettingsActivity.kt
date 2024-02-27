package com.vlv.configuration.presentation.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.configuration.presentation.R
import com.vlv.imperiya.core.ui.bottomsheet.Item
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
            data { options ->
                updateOptions(options)
            }
            error { _ ->

            }
        }
    }

    private fun loadConfig() {
        viewModel.config().observe(this) {}
    }

    private fun updateOptions(options: List<SectionUIItem>) {
        adapter.submitList(options)
    }

    private fun setupRecyclerView() {
        items.layoutManager = LinearLayoutManager(this)
        items.adapter = adapter
    }

    private fun onClickItem(settingsItem: SectionUIItem) {
        val data = (settingsItem.data as? ConfigDataList) ?: return

        val context: Context = this

        SpinnerBottomSheet(canDismiss = true).apply {
            setTitleText(data.title.toString())
            setItems(data.items.map {
                Item(it.title.toString(), it.value, it == data.selectedItem, it.id)
            })

            setOnClickConfirm { item ->
                val newItem = data.items.find { item?.id == it.id } ?: return@setOnClickConfirm
                viewModel.setData(
                    settingsItem.copy(
                        data = data.copy(
                            selectedItem = newItem
                        )
                    )
                )
                items.announceForAccessibility(getString(
                        com.vlv.configuration.domain.R.string.configuration_options_change_configuration
                    ))
                dismiss()
            }
            setButtonText(context.getString(R.string.configuration_button))
        }.show(supportFragmentManager, SettingsActivity::class.java.name)
    }

    private fun onClickSwitch(settingsItem: SectionUIItem, checkbox: Boolean) {
        viewModel.setData(
            settingsItem.copy(
                data = checkbox
            )
        )
    }

}