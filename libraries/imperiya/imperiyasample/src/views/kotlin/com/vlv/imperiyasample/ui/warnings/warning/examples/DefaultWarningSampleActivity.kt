package com.vlv.imperiyasample.ui.warnings.warning.examples

import androidx.appcompat.app.AppCompatActivity
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.core.ui.warning.WarningView
import com.vlv.imperiyasample.R

abstract class DefaultWarningSampleActivity : AppCompatActivity(R.layout.activity_warning_default_sample) {

    protected val warningView: WarningView by viewProvider(R.id.warning_view)

}