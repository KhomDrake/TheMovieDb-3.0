package com.vlv.common.ui.listing

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.vlv.extensions.stateEmpty
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.ui.stateview.StateView
import com.vlv.imperiya.ui.warning.SmallWarningView

abstract class ListingItemsFragment : Fragment(R.layout.common_fragment_listing) {

    protected val viewStateMachine = ViewStateMachine()

    protected val root: ViewGroup by viewProvider(R.id.root)
    protected val items: RecyclerView by viewProvider(R.id.listing_content)
    protected val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_listing)
    protected val emptyState: StateView by viewProvider(R.id.empty_view_listing)
    protected val warningView: SmallWarningView by viewProvider(R.id.warning_view_listing)

    abstract val adapter: PagingDataAdapter<*, *>
    abstract val loadingLayout: Int

    abstract fun loaderAdapter() : LoaderAdapter

    abstract fun configEmptyState()

    abstract fun configWarningView()

    open fun createLayoutManager() : RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 2)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configLoading()
        configWarningView()
        configEmptyState()
        configRecyclerView()
        configViewStateMachine()
    }

    private fun configLoading() {
        View.inflate(requireContext(), loadingLayout, shimmer)
    }

    private fun configViewStateMachine() {
        viewStateMachine.setup {
            defaultConfig(root)

            stateData {
                visibles(items)
                gones(shimmer, warningView, emptyState)
            }

            stateError {
                visibles(warningView)
                gones(shimmer, items, emptyState)
            }

            stateLoading {
                visibles(shimmer)
                gones(items, warningView, emptyState)
            }

            stateEmpty {
                visibles(emptyState)
                gones(items, warningView, shimmer)
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
            onError = {
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