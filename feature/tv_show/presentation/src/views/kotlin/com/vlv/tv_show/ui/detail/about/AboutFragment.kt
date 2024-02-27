package com.vlv.tv_show.ui.detail.about

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.ui.DetailActivity
import com.vlv.extensions.dataState
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.core.ui.warning.SmallWarningView
import com.vlv.tv_show.R
import org.koin.androidx.viewmodel.ext.android.viewModel

const val EXTRA_TV_SHOW = "EXTRA_TV_SHOW"
class AboutFragment : Fragment(R.layout.tv_show_fragment_about) {

    private val viewModel: AboutViewModel by viewModel()
    private val series: TvShow? by extraProvider(EXTRA_TV_SHOW, null)

    private val root: ViewGroup by viewProvider(R.id.root)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_about)
    private val content: RecyclerView by viewProvider(R.id.content)
    private val warningView: SmallWarningView by viewProvider(R.id.warning_view_about)

    private val viewStateMachine = ViewStateMachine()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewStateMachine()
        setupRecyclerView()
        setupWarningView()
        loadSeriesDetail()
    }

    private fun setupWarningView() {
        warningView.setOnClickLink {
            loadSeriesDetail()
        }
    }

    private fun setupRecyclerView() {
        content.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        content.adapter = AboutAdapter()
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            defaultConfig(root)

            stateData {
                visibles(content)
                gones(shimmer, warningView)
            }

            stateLoading {
                visibles(shimmer)
                gones(content, warningView)
            }

            stateError {
                visibles(warningView)
                gones(shimmer, content)
            }
        }
    }

    private fun loadSeriesDetail() {
        val series = series ?: return

        viewModel.seriesDetail(resources, series.id).observe(viewLifecycleOwner) {
            data { detail ->
                (content.adapter as? AboutAdapter)?.submitList(detail.aboutItems)
                (activity as? DetailActivity)?.showExpandedInfo(
                    detail.score,
                    detail.dateAndTime
                )
                viewStateMachine.dataState()
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { e ->
                viewStateMachine.errorState()
            }
        }
    }

    override fun onDestroyView() {
        viewStateMachine.shutdown()
        super.onDestroyView()
    }


    companion object {
        fun instance(series: TvShow) = AboutFragment().apply {
            arguments = bundleOf(
                EXTRA_TV_SHOW to series
            )
        }
    }

}