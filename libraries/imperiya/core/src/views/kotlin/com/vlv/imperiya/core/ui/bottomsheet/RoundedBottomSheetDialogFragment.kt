package com.vlv.imperiya.core.ui.bottomsheet

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vlv.imperiya.core.R

abstract class RoundedBottomSheetDialogFragment(
    protected val canDismiss: Boolean = false
) : BottomSheetDialogFragment() {

    abstract val layoutRes: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Imperiya_ActionSheet_Rounded)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(canDismiss)
        dialog?.setCancelable(canDismiss)
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        dialog.setCanceledOnTouchOutside(canDismiss)
        dialog.setCancelable(canDismiss)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    override fun getTheme() = R.style.Imperiya_ActionSheet_Rounded
}
