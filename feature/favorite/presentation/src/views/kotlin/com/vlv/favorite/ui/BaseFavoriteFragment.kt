package com.vlv.favorite.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.stateData
import com.vlv.extensions.stateEmpty
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.favorite.R
import com.vlv.imperiya.core.ui.stateview.StateView
import com.vlv.imperiya.core.ui.warning.SmallWarningView

abstract class BaseFavoriteFragment : Fragment(R.layout.favorite_listing_fragment) {

    protected val viewStateMachine = ViewStateMachine()

    private val root: ViewGroup by viewProvider(R.id.root)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_listing)
    protected val items: RecyclerView by viewProvider(R.id.items)
    protected val emptyState: StateView by viewProvider(R.id.empty_view)
    protected val error: SmallWarningView by viewProvider(R.id.small_warning_view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        View.inflate(requireContext(), loadingLayout, shimmer)
        setupRecyclerView()
        setupStateView()
        setupViewStateMachine()
        error.setOnClickLink {
            loadFavorites()
        }
    }

    override fun onStart() {
        super.onStart()
        loadFavorites()
    }

    abstract val loadingLayout: Int

    abstract fun setupStateView()

    abstract fun setupRecyclerView()

    abstract fun loadFavorites()

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            defaultConfig(root)

            stateData {
                visibles(items)
                gones(shimmer, error, emptyState)
            }

            stateError {
                visibles(error)
                gones(shimmer, items, emptyState)
            }

            stateLoading {
                visibles(shimmer)
                gones(items, error, emptyState)
            }

            stateEmpty {
                visibles(emptyState)
                gones(items, error, shimmer)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewStateMachine.shutdown()
    }

}