package com.vlv.common.ui.listing

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.common.R
import com.vlv.common.ui.adapter.LoaderAdapter
import com.vlv.extensions.dataState
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.extensions.setupState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.ui.warning.WarningView

abstract class ListingItemsActivity : AppCompatActivity(R.layout.common_listing_activity) {

    protected val viewStateMachine = ViewStateMachine()

    protected val root: ViewGroup by viewProvider(R.id.root)
    protected val items: RecyclerView by viewProvider(R.id.items)
    protected val shimmer: ShimmerFrameLayout by viewProvider(R.id.loading)
    protected val warningView: WarningView by viewProvider(R.id.warning_view)
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)

    abstract val title: Int
    abstract val adapter: PagingDataAdapter<*, *>
    abstract val loadingLayout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configToolbar()
        configLoading()
        configViewStateMachine()
        configWarningView()
        configRecyclerView()
    }

    abstract fun loaderAdapter() : LoaderAdapter

    abstract fun configWarningView()

    open fun createLayoutManager() : LayoutManager = GridLayoutManager(this, 2)

    private fun configToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.navigationContentDescription = getString(R.string.common_back_content_description)

        val titleText = getString(title)
        toolbar.title = titleText
    }

    private fun configLoading() {
        View.inflate(this, loadingLayout, shimmer)
    }

    private fun configViewStateMachine() {
        viewStateMachine.setup {
            defaultConfig(root)

            stateData {
                visibles(items, toolbar)
                gones(shimmer, warningView)
            }

            stateError {
                visibles(warningView)
                gones(shimmer, items, toolbar)
            }

            stateLoading {
                visibles(shimmer, toolbar)
                gones(items, warningView)
            }
        }
    }

    private fun configRecyclerView() {
        val pagingAdapter = adapter
        items.layoutManager = createLayoutManager()
        items.adapter = adapter.withLoadStateFooter(loaderAdapter())

        pagingAdapter.setupState(
            onData = {
                viewStateMachine.dataState()
            },
            onEmpty = {
                viewStateMachine.emptyState()
            },
            onError = { _ ->
                viewStateMachine.errorState()
            },
            onLoading = {
                viewStateMachine.loadingState()
            }
        )
    }


    override fun onDestroy() {
        viewStateMachine.shutdown()
        super.onDestroy()
    }

}