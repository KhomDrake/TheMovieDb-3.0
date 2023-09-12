package com.vlv.series.ui.detail.cast

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.series.R
import com.vlv.series.data.Series

class CastFragment : Fragment(R.layout.series_fragment_about) {

//    protected val title: AppCompatTextView by viewProvider(R.id.title)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        title.text = "Cast"
    }

    companion object {
        fun instance(series: Series) = CastFragment().apply {
            arguments = bundleOf(
                "series" to series
            )
        }
    }

}