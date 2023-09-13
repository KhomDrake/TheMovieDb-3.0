package com.vlv.series.ui.detail.cast

import androidx.core.os.bundleOf
import br.com.arch.toolkit.delegate.extraProvider
import com.vlv.common.ui.cast.CastFragment
import com.vlv.common.ui.cast.adapter.CastAdapter
import com.vlv.extensions.dataState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.series.data.Series
import com.vlv.series.ui.detail.about.EXTRA_SERIES
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeriesCastFragment : CastFragment() {

    private val series: Series? by extraProvider(EXTRA_SERIES, null)

    private val viewModel: SeriesCastViewModel by viewModel()

    override fun loadCast() {
        val series = series ?: return
        viewModel.cast(series.id).observe(viewLifecycleOwner) {
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

    companion object {
        fun instance(series: Series) = SeriesCastFragment().apply {
            arguments = bundleOf(
                EXTRA_SERIES to series
            )
        }
    }

}