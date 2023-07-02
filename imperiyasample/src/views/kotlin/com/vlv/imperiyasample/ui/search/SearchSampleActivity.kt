package com.vlv.imperiyasample.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.ui.ImperiyaSearchView
import com.vlv.imperiya.ui.ImperiyaToolbarView
import com.vlv.imperiyasample.R

class SearchSampleActivity : AppCompatActivity(R.layout.activity_search_sample) {

    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val search: ImperiyaSearchView by viewProvider(R.id.search)
    private val searchThree: ImperiyaToolbarView by viewProvider(R.id.search_three)
    private val changed: AppCompatTextView by viewProvider(R.id.text_changed)
    private val submit: AppCompatTextView by viewProvider(R.id.text_submit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        search.setHint("Input Text")
        search.onQuery(
            onTextChanged = {
                changed.text = "Text Changed: $it"
            },
            onTextSubmit = {
                submit.text = "Text Submit: $it"
            }
        )

        searchThree.addSearchView()
        searchThree.searchView?.apply {
            setHint("Text")
        }

    }

}