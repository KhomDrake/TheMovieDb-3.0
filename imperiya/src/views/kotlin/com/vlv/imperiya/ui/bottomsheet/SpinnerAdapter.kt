package com.vlv.imperiya.ui.bottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.R
import kotlin.random.Random

data class Item(
    val name: String,
    val value: String,
    var checked: Boolean = false,
    val id: Int = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE)
)

class ItemDiffUtil: DiffUtil.ItemCallback<Item>() {
    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.value == newItem.value && oldItem.name == newItem.name
            && oldItem.checked == newItem.checked
    }

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }
}

class SpinnerAdapter(
    private val onItemSelected: (Item) -> Unit,
    private val onItemClicked: (Item?) -> Unit
) : ListAdapter<Item, ItemViewHolder>(ItemDiffUtil()) {

    private var selectedPosition: Int = -1

    val selectedItem: Item?
        get() = if(selectedPosition == -1) null else currentList[selectedPosition]

    override fun submitList(list: MutableList<Item>?) {
        selectedPosition = list?.indexOfFirst { it.checked } ?: -1
        super.submitList(list)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)

        val onCheckedChange: (Boolean) -> Unit = { checked ->
            if (checked) {
                if (selectedPosition != -1 && selectedPosition != holder.adapterPosition) {
                    currentList[selectedPosition]?.checked = false
                    notifyItemChanged(selectedPosition)
                }

                onItemSelected.invoke(item)

                item.checked = false
                selectedPosition = holder.adapterPosition
            }
        }

        holder.itemView.setOnClickListener {
            val changed = !holder.checkbox.isChecked
            holder.checkbox.isChecked = changed
            onCheckedChange.invoke(changed)
            onItemClicked.invoke(selectedItem)
        }
        holder.checkbox.setOnCheckedChangeListener { _, checked ->
            onCheckedChange.invoke(checked)
            onItemClicked.invoke(selectedItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.imperiya_spinner_action_sheet_item, parent, false)
        )
    }

}

class ItemViewHolder(view: View): ViewHolder(view) {

    val title: AppCompatTextView by viewProvider(R.id.small_warning_title)
    val checkbox: AppCompatCheckBox by viewProvider(R.id.checkbox)

    fun bind(item: Item) {
        checkbox.isChecked = item.checked
        title.text = item.name
    }

}