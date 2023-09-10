package com.vlv.movie.ui.detail.cast

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.StateMachine
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.extensions.State
import com.vlv.extensions.dataState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.ui.warning.WarningView
import com.vlv.movie.R
import com.vlv.movie.data.Movie
import com.vlv.movie.ui.detail.cast.adapter.CastAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

const val EXTRA_MOVIE = "EXTRA_MOVIE"

class CastFragment : Fragment(R.layout.movie_fragment_cast) {

    private val viewModel: CastViewModel by viewModel()

    private val cast: RecyclerView by viewProvider(R.id.cast_content)
    private val root: ViewGroup by viewProvider(R.id.root)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_cast)
    private val warningView: WarningView by viewProvider(R.id.warning_view_cast)

    private val movie: Movie? by extraProvider(EXTRA_MOVIE)

    private val viewStateMachine = ViewStateMachine()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewStateMachine()
        setupRecyclerView()
        loadCast()
        warningView.setOnTryAgain {
            loadCast()
        }
    }

    private fun loadCast() {
        val movie = movie ?: return
        viewModel.movieCast(movie.id).observe(viewLifecycleOwner) {
            data { castList ->
                viewStateMachine.dataState()
                (cast.adapter as? CastAdapter)?.submitList(castList)
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
            config {
                initialState = State.LOADING.ordinal
                onChangeState = StateMachine.OnChangeStateCallback {
                    TransitionManager.beginDelayedTransition(root)
                }
            }

            stateData {
                visibles(cast)
                gones(warningView, shimmer)
            }

            stateLoading {
                visibles(shimmer)
                gones(warningView, cast)
            }

            stateError {
                visibles(warningView)
                gones(cast, shimmer)
            }
        }
    }

    private fun setupRecyclerView() {
        cast.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        cast.adapter = CastAdapter()
    }

    override fun onDestroyView() {
        viewStateMachine.shutdown()
        super.onDestroyView()
    }

    companion object {

        fun instance(movie: Movie) = CastFragment().apply {
            arguments = bundleOf(
                EXTRA_MOVIE to movie
            )
        }

    }

}