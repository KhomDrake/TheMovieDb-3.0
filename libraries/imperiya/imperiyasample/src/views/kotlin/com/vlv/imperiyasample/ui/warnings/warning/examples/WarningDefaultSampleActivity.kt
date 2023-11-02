package com.vlv.imperiyasample.ui.warnings.warning.examples

import android.os.Bundle
import android.widget.Toast

class WarningDefaultSampleActivity : DefaultWarningSampleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        warningView.apply {
            setOnTryAgain {
                Toast.makeText(this@WarningDefaultSampleActivity, "Clicked", Toast.LENGTH_SHORT).show()
            }
            setCloseIcon {
                finish()
            }
        }
    }

}