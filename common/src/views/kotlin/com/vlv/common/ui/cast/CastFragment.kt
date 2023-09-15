package com.vlv.common.ui.cast

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.StateMachine
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.common.R
import com.vlv.common.ui.cast.adapter.CastAdapter
import com.vlv.extensions.State
import com.vlv.extensions.stateData
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.ui.warning.SmallWarningView

abstract class CastFragment : Fragment(R.layout.common_fragment_cast) {

    protected val cast: RecyclerView by viewProvider(R.id.cast_content)
    protected val root: ViewGroup by viewProvider(R.id.root)
    protected val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_cast)
    protected val warningView: SmallWarningView by viewProvider(R.id.warning_view_cast)

    protected val viewStateMachine = ViewStateMachine()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewStateMachine()
        setupRecyclerView()
        loadCast()
        warningView.setOnClickLink {
            loadCast()
        }
    }

    abstract fun loadCast()

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

}