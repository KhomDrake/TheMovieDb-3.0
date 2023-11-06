package com.vlv.series.ui.detail.season

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.data.series.Series
import com.vlv.series.R
import com.vlv.series.ui.detail.about.EXTRA_SERIES
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeasonsFragment : Fragment(R.layout.series_fragment_seasons) {

    private val viewModel: SeasonsViewModel by viewModel()

    private val series: Series? by extraProvider(EXTRA_SERIES, null)

    private val seasons: RecyclerView by viewProvider(R.id.seasons_items)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val series = series ?: return
        seasons.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        seasons.adapter = SeasonAdapter()
        viewModel.seriesDetail(series.id).observe(viewLifecycleOwner) {
            data {
                (seasons.adapter as? SeasonAdapter)?.submitList(it)
            }
            showLoading {

            }
            error { _ ->

            }
        }
    }

    companion object {
        fun instance(series: Series) = SeasonsFragment().apply {
            arguments = bundleOf(
                EXTRA_SERIES to series
            )
        }
    }

}