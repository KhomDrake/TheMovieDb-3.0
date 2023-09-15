package com.vlv.movie.ui.detail.about

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
import com.vlv.common.ui.DetailActivity
import com.vlv.extensions.dataState
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.ui.warning.SmallWarningView
import com.vlv.movie.R
import com.vlv.movie.data.Movie
import com.vlv.movie.ui.detail.cast.EXTRA_MOVIE
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : Fragment(R.layout.movie_fragment_about) {

    private val movie: Movie? by extraProvider(EXTRA_MOVIE)

    private val viewModel: AboutMovieViewModel by viewModel()

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
        loadMovieDetail()
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

    private fun setupRecyclerView() {
        content.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        content.adapter = AboutAdapter()
    }

    private fun setupWarningView() {
        warningView.setOnClickLink {
            loadMovieDetail()
        }
    }

    private fun loadMovieDetail() {
        val movie = movie ?: return

        viewModel.movieDetail(resources, movie.id).observe(viewLifecycleOwner) {
            data { detail ->
                (content.adapter as? AboutAdapter)?.submitList(detail.items)
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

        fun instance(movie: Movie) = AboutFragment().apply {
            arguments = bundleOf(
                EXTRA_MOVIE to movie
            )
        }

    }

}