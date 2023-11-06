package com.vlv.imperiyasample.ui.stateview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiyasample.R

class StateViewSampleActivity : AppCompatActivity(R.layout.activity_state_sample) {

    private val toolbar: Toolbar by viewProvider(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

}