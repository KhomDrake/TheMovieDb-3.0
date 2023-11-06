package com.vlv.imperiyasample.ui.warnings.warning.examples

import android.os.Bundle
import android.widget.Toast

class WarningWithoutIconSampleActivity : DefaultWarningSampleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        warningView.apply {
            setVisibilityIcon(show = false)
            setOnTryAgain {
                Toast.makeText(this@WarningWithoutIconSampleActivity, "Clicked", Toast.LENGTH_SHORT).show()
            }
            setCloseIcon {
                finish()
            }
        }
    }

}