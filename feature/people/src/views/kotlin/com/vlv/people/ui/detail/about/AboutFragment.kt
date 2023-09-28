package com.vlv.people.ui.detail.about

import android.os.Bundle
import android.util.Log
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
import com.vlv.common.data.people.People
import com.vlv.common.ui.route.EXTRA_PEOPLE
import com.vlv.extensions.dataState
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.ui.warning.SmallWarningView
import com.vlv.people.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : Fragment(R.layout.people_about_fragment) {

    private val viewModel: AboutViewModel by viewModel()

    private val people: People? by extraProvider(EXTRA_PEOPLE, null)

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
        val people = people ?: return

        viewModel.peopleDetail(resources, people.id).observe(viewLifecycleOwner) {
            data { items ->
                (content.adapter as? AboutAdapter)?.submitList(items)
                viewStateMachine.dataState()
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { e ->
                Log.i("Vini", e.stackTraceToString())
                viewStateMachine.errorState()
            }
        }
    }

    override fun onDestroyView() {
        viewStateMachine.shutdown()
        super.onDestroyView()
    }

    companion object {
        fun instance(people: People) = AboutFragment().apply {
            arguments = bundleOf(
                EXTRA_PEOPLE to people
            )
        }
    }

}