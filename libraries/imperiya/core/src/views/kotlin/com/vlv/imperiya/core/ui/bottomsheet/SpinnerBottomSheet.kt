package com.vlv.imperiya.core.ui.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.core.R

class SpinnerBottomSheet(
    canDismiss: Boolean = false
) : RoundedBottomSheetDialogFragment(canDismiss) {

    override val layoutRes: Int
        get() = R.layout.imperiya_spinner_action_sheet

    private val title: AppCompatTextView by viewProvider(R.id.action_sheet_title)
    private val body: AppCompatTextView by viewProvider(R.id.action_sheet_body)
    private val button: AppCompatButton by viewProvider(R.id.confirm_button)
    private val items: RecyclerView by viewProvider(R.id.items)

    private var titleText: String? = null
    private var bodyText: String? = null
    private var buttonText: String? = null
    private var onClickConfirm: ((Item?) -> Unit)? = null
    private var onItemSelected: ((Item) -> Unit) = {}
    private var itemsToBeSelected = mutableListOf<Item>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.text = titleText
        body.text = bodyText
        button.text = buttonText
        body.isVisible = bodyText != null

        items.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SpinnerAdapter(onItemSelected) {
            button.isEnabled = it != null
        }
        adapter.submitList(itemsToBeSelected)

        items.adapter = adapter

        button.setOnClickListener {
            onClickConfirm?.invoke(adapter.selectedItem)
        }
        allowEnterTransitionOverlap = true
    }

    fun setItems(items: List<Item>) = apply {
        itemsToBeSelected = items.toMutableList()
    }

    fun setTitleText(newTitle: String) = apply {
        titleText = newTitle
    }

    fun setBodyText(newBody: String) = apply {
        bodyText = newBody
    }

    fun setButtonText(newButtonText: String) = apply {
        buttonText = newButtonText
    }

    fun setOnClickConfirm(onClick: (Item?) -> Unit) = apply {
        onClickConfirm = onClick
    }

    fun setOnSelectItem(onClick: (Item) -> Unit) = apply {
        onItemSelected = onClick
    }

}