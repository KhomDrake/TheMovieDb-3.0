package com.vlv.people.ui.detail.seriescredit

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.common.data.people.People
import com.vlv.common.data.series.toDetailObject
import com.vlv.common.ui.adapter.series.SeriesAdapter
import com.vlv.common.ui.route.EXTRA_PEOPLE
import com.vlv.common.ui.route.toSeriesDetail
import com.vlv.extensions.dataState
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateEmpty
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.core.ui.stateview.StateView
import com.vlv.imperiya.core.ui.warning.SmallWarningView
import com.vlv.people.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeriesCreditFragment : Fragment(R.layout.people_credit_listing) {

    private val viewModel: SeriesCreditViewModel by viewModel()

    private val viewStateMachine = ViewStateMachine()
    private val people: People? by extraProvider(EXTRA_PEOPLE, null)

    private val root: ViewGroup by viewProvider(R.id.root)
    private val items: RecyclerView by viewProvider(R.id.items)
    private val emptyState: StateView by viewProvider(R.id.empty_view)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_listing)
    private val error: SmallWarningView by viewProvider(R.id.small_warning_view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        View.inflate(requireContext(), com.vlv.ui.R.layout.common_listing_movie_loading, shimmer)
        setupRecyclerView()
        setupStateView()
        setupViewStateMachine()
        loadCredits()
        error.setOnClickLink {
            loadCredits()
        }
    }

    private fun setupStateView() {
        emptyState.apply {
            setStateIcon(com.vlv.imperiya.core.R.drawable.ic_tv)
            setTitle(R.string.people_detail_empty_state_series_title)
        }
    }

    private fun setupRecyclerView() {
        items.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        items.adapter = SeriesAdapter { series, view ->
            val intent = requireContext().toSeriesDetail(series.toDetailObject())
            startActivity(
                intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    view,
                    getString(com.vlv.ui.R.string.common_poster_transition_name)
                ).toBundle()
            )
        }
    }

    private fun loadCredits() {
        val people = people ?: return

        viewModel.seriesCredit(people.id).observe(viewLifecycleOwner) {
            data {
                if(it.isEmpty()) viewStateMachine.emptyState() else viewStateMachine.dataState()

                (items.adapter as? SeriesAdapter)?.submitList(it)
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

    companion object {
        fun instance(people: People) = SeriesCreditFragment().apply {
            arguments = bundleOf(EXTRA_PEOPLE to people)
        }
    }

}