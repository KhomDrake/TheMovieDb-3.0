package com.vlv.series.ui.detail.season

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.series.R
import com.vlv.series.data.Series

class SeasonsFragment : Fragment(R.layout.series_fragment_about) {

//    protected val title: AppCompatTextView by viewProvider(R.id.title)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        title.text = "Seasons"
    }

    companion object {
        fun instance(series: Series) = SeasonsFragment().apply {
            arguments = bundleOf(
                "series" to series
            )
        }
    }

}