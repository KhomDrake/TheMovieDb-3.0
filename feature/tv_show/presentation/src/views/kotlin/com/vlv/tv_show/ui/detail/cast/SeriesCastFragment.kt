package com.vlv.tv_show.ui.detail.cast

import androidx.core.os.bundleOf
import br.com.arch.toolkit.delegate.extraProvider
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.ui.cast.CastFragment
import com.vlv.common.ui.cast.adapter.CastAdapter
import com.vlv.extensions.dataState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.tv_show.ui.detail.about.EXTRA_TV_SHOW
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeriesCastFragment : CastFragment() {

    private val series: TvShow? by extraProvider(EXTRA_TV_SHOW, null)

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
        fun instance(series: TvShow) = SeriesCastFragment().apply {
            arguments = bundleOf(
                EXTRA_TV_SHOW to series
            )
        }
    }

}