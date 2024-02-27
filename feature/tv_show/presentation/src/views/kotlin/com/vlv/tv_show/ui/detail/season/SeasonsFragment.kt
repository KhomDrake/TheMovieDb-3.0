package com.vlv.tv_show.ui.detail.season

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.data.tv_show.TvShow
import com.vlv.tv_show.R
import com.vlv.tv_show.ui.detail.about.EXTRA_TV_SHOW
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeasonsFragment : Fragment(R.layout.tv_show_fragment_seasons) {

    private val viewModel: SeasonsViewModel by viewModel()

    private val series: TvShow? by extraProvider(EXTRA_TV_SHOW, null)

    private val seasons: RecyclerView by viewProvider(R.id.seasons_items)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val series = series ?: return
        seasons.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        seasons.adapter = SeasonAdapter()
        viewModel.seriesDetail(resources, series.id).observe(viewLifecycleOwner) {
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
        fun instance(series: TvShow) = SeasonsFragment().apply {
            arguments = bundleOf(
                EXTRA_TV_SHOW to series
            )
        }
    }

}