package com.vlv.common.ui.search

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.ui.R
import com.vlv.common.ui.adapter.LoaderAdapter
import com.vlv.common.ui.adapter.searchhistory.HistoryAdapter
import com.vlv.common.ui.adapter.searchhistory.HistoryItems
import com.vlv.extensions.State
import com.vlv.extensions.dataState
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.initialState
import com.vlv.extensions.isInInitialState
import com.vlv.extensions.loadingState
import com.vlv.extensions.setupState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateEmpty
import com.vlv.extensions.stateError
import com.vlv.extensions.stateInitial
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.core.ui.search.ImperiyaSearchView
import com.vlv.imperiya.core.ui.stateview.StateView
import com.vlv.imperiya.core.ui.warning.SmallWarningView
import com.vlv.data.network.database.data.History
import com.vlv.imperiya.core.ui.search.ImperiyaToolbarView

abstract class SearchActivity : AppCompatActivity(R.layout.common_search_activity) {

    protected val root: ViewGroup by viewProvider(R.id.root)
    protected val toolbarView: ImperiyaToolbarView by viewProvider(R.id.search)
    protected val historyItems: RecyclerView by viewProvider(R.id.history_items)
    protected val items: RecyclerView by viewProvider(R.id.items)
    private val warning: SmallWarningView by viewProvider(R.id.warning_view)
    private val emptyState: StateView by viewProvider(R.id.state_view)
    private val loading: ShimmerFrameLayout by viewProvider(R.id.loading)

    protected val viewStateMachine = ViewStateMachine()

    abstract val loadingLayout: Int
    abstract val titleHistoryTitle: Int
    abstract val searchHint: Int
    abstract val adapter: PagingDataAdapter<*, *>

    abstract fun onTextSubmit(query: String?)

    abstract fun addHistory(text: String?)

    abstract fun removeHistory(history: History)

    abstract fun loadHistory()

    abstract fun createLayoutManager() : RecyclerView.LayoutManager

    abstract fun loaderAdapter() : LoaderAdapter

    abstract fun configStateView(stateView: StateView)

    abstract fun configWarningView(smallWarningView: SmallWarningView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLoadingLayout()
        setupViewStateMachine()
        setupHistoryItems()
        setupSearchView()
        setupAdapter()
        configStateView(emptyState)
        loadHistory()
        warning.setOnClickLink {
            adapter.retry()
        }
    }

    protected fun updateHistory(items: List<HistoryItems>) {
        (historyItems.adapter as? HistoryAdapter)?.submitList(items)
        historyItems.isVisible = items.isEmpty().not() && viewStateMachine.isInInitialState()
    }

    private fun setupAdapter() {
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
            onError = { e ->
                viewStateMachine.errorState()
            },
            onLoading = {
                viewStateMachine.loadingState()
            }
        )
    }

    private fun setupSearchView() {
        toolbarView.setNavigationOnClickListener {
            finishAfterTransition()
        }
        toolbarView.searchView.apply {
            setCloseIcon(com.vlv.imperiya.core.R.drawable.ic_close)
            setHint(searchHint)
            setup(onTextSubmit = {
                addHistory(it)
                onTextSubmit(it)
            })
            onClickCloseListener {
                clearText()
                viewStateMachine.initialState()
                loadHistory()
            }
        }
    }

    private fun setupHistoryItems() {
        historyItems.layoutManager = LinearLayoutManager(this)
        historyItems.adapter = HistoryAdapter(
            onClick = { history ->
                toolbarView.searchView.setText(history.text)
                onTextSubmit(history.text)
            },
            onClickClose = { history ->
                removeHistory(history)
            }
        )
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            defaultConfig(root, State.INITIAL)

            stateInitial {
                visibles(historyItems)
                gones(items, loading, warning, emptyState)
            }

            stateData {
                visibles(items)
                gones(loading, warning, historyItems, emptyState)
            }

            stateError {
                visibles(warning)
                gones(loading, items, historyItems, emptyState)
            }

            stateEmpty {
                visibles(emptyState)
                gones(loading, warning, historyItems, items)
            }

            stateLoading {
                visibles(loading)
                gones(items, warning, historyItems, emptyState)
            }
        }
    }

    private fun setupLoadingLayout() {
        View.inflate(this, loadingLayout, loading)
    }

    override fun onDestroy() {
        viewStateMachine.shutdown()
        super.onDestroy()
    }

}