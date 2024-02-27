package com.vlv.genre.presentation.ui.series

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vlv.extensions.dataState
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.genre.R
import com.vlv.imperiya.core.ui.warning.SmallWarningView
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeriesGenreActivity : AppCompatActivity(R.layout.genre_items_genre_activity) {

    private val viewModel: SeriesGenreViewModel by viewModel()

    private val root: ViewGroup by viewProvider(R.id.root)
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val tabs: TabLayout by viewProvider(R.id.tabs)
    private val layout: ViewPager2 by viewProvider(R.id.layout)
    private val loading: ShimmerFrameLayout by viewProvider(R.id.loading)
    private val error: SmallWarningView by viewProvider(R.id.error)
    private val viewStateMachine = ViewStateMachine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.title = getString(R.string.genre_tv_show_toolbar_title)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.navigationContentDescription = getString(com.vlv.ui.R.string.common_back_content_description)

        setupViewStateMachine()
        loadGenres()
        error.setOnClickLink {
            loadGenres()
        }
    }

    private fun loadGenres() {
        viewModel.genres().observe(this) {
            data {
                val adapter = SeriesByGenreAdapter(it, this@SeriesGenreActivity)
                layout.adapter = adapter
                TabLayoutMediator(tabs, layout) { tab, position ->
                    tab.text = it[position].name
                }.attach()
                it.forEach { genre ->
                    tabs.addTab(
                        tabs.newTab().apply {
                            this.text = genre.name
                        }
                    )
                }
                viewStateMachine.dataState()
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { _ ->
                viewStateMachine.errorState()
            }
        }
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            defaultConfig(root)

            stateData {
                visibles(layout, tabs)
                gones(loading, error)
            }

            stateLoading {
                visibles(loading)
                gones(layout, tabs, error)
            }

            stateError {
                visibles(error)
                gones(layout, tabs, loading)
            }
        }
    }

    override fun onDestroy() {
        viewStateMachine.shutdown()
        super.onDestroy()
    }

}