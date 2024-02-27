package com.vlv.configuration.presentation.ui

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.data.SectionUIType
import com.vlv.configuration.presentation.R
import com.vlv.extensions.addAccessibilityDelegate
import com.vlv.extensions.addHeadingAccessibilityDelegate
import com.vlv.extensions.inflate

class SettingsItemDiffUtil: DiffUtil.ItemCallback<SectionUIItem>() {
    override fun areContentsTheSame(oldItem: SectionUIItem, newItem: SectionUIItem): Boolean {
        return oldItem.type == newItem.type
            && oldItem.title == newItem.title
            && oldItem.description == newItem.description
            && oldItem.descriptionWithSelectedValue == newItem.descriptionWithSelectedValue
    }

    override fun areItemsTheSame(oldItem: SectionUIItem, newItem: SectionUIItem): Boolean {
        return oldItem.id == newItem.id
    }
}

class SettingsAdapter(
    private val onClickIem: (SectionUIItem) -> Unit,
    private val onSwitch: (SectionUIItem, Boolean) -> Unit
) : ListAdapter<SectionUIItem, ViewHolder>(SettingsItemDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.ordinal
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        when(holder) {
            is SettingsTitleViewHolder -> holder.bind(item)
            is SettingsNormalViewHolder -> {
                holder.bind(item)
                holder.itemView.setOnClickListener {
                    onClickIem.invoke(item)
                }
                holder.itemView.addAccessibilityDelegate(
                    com.vlv.configuration.domain.R.string.configuration_options_item_description
                )
            }
            is SettingsSwitchViewHolder -> {
                holder.bind(item)
                holder.switch.setOnClickListener {
                    onSwitch.invoke(item, holder.switch.isChecked)
                }
            }
            else -> Unit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            SectionUIType.HEADER.ordinal ->
                SettingsTitleViewHolder(
                    parent.inflate(R.layout.configuration_settings_title_item)
                )
            SectionUIType.SWITCH.ordinal ->
                SettingsSwitchViewHolder(
                    parent.inflate(R.layout.configuration_settings_normal_item)
                )
            else ->
                SettingsNormalViewHolder(
                    parent.inflate(R.layout.configuration_settings_normal_item)
                )
        }
    }

}

class SettingsTitleViewHolder(view: View): ViewHolder(view) {
    fun bind(settingsItem: SectionUIItem) {
        (itemView as? AppCompatTextView)?.apply {
            text = settingsItem.title
            addHeadingAccessibilityDelegate()
        }
    }
}

class SettingsSwitchViewHolder(view: View): ViewHolder(view) {

    private val title: AppCompatTextView by viewProvider(R.id.settings_title)
    private val body: AppCompatTextView by viewProvider(R.id.settings_body)
    val switch: SwitchCompat by viewProvider(R.id.switch_settings)

    fun bind(settingsItem: SectionUIItem) {
        title.text = settingsItem.title
        body.text = settingsItem.description

        body.isVisible = settingsItem.description.isNullOrBlank().not()

        switch.isChecked = (settingsItem.data as? Boolean) ?: false
        switch.isVisible = true
    }

}

class SettingsNormalViewHolder(view: View): ViewHolder(view) {

    private val title: AppCompatTextView by viewProvider(R.id.settings_title)
    private val body: AppCompatTextView by viewProvider(R.id.settings_body)
    val switch: SwitchCompat by viewProvider(R.id.switch_settings)

    fun bind(settingsItem: SectionUIItem) {
        title.text = settingsItem.title
        body.text = settingsItem.descriptionWithSelectedValue

        body.isVisible = settingsItem.description.isNullOrBlank().not()

        switch.isVisible = false
    }
}