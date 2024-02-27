package com.vlv.configuration.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.configuration.presentation.R
import com.vlv.extensions.dataState
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.core.ui.bottomsheet.Item
import com.vlv.imperiya.core.ui.bottomsheet.SpinnerBottomSheet
import com.vlv.imperiya.core.ui.warning.WarningView
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity(R.layout.configuration_settings_activity) {

    private val viewModel: SettingsViewModel by viewModel()

    private val root: ViewGroup by viewProvider(R.id.root)
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val items: RecyclerView by viewProvider(R.id.items)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_settings)
    private val errorState: WarningView by viewProvider(R.id.error_state_settings)
    private val viewStateMachine = ViewStateMachine()

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

        setupViewStateMachine()
        setupErrorState()
        setupRecyclerView()
        setupConfig()
        loadConfig()
    }

    private fun setupErrorState() {
        errorState.setOnTryAgain {
            loadConfig()
        }
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            defaultConfig(root)

            stateData {
                visibles(items, toolbar)
                gones(shimmer, errorState)
            }

            stateError {
                visibles(errorState)
                gones(shimmer, items, toolbar)
            }

            stateLoading {
                visibles(shimmer, toolbar)
                gones(items, errorState)
            }
        }
    }

    private fun setupConfig() {
        viewModel.config.observe(this) {
            data { options ->
                if(options.isEmpty()) viewStateMachine.errorState()
                else viewStateMachine.dataState()

                updateOptions(options)
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { _ ->
                viewStateMachine.errorState()
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

    override fun onDestroy() {
        viewStateMachine.shutdown()
        super.onDestroy()
    }

}