package com.vlv.themoviedb.ui.menu

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.extensions.inflate
import com.vlv.themoviedb.R
import kotlin.random.Random

enum class MenuItemType {
    HEADER,
    ITEM
}

class MenuItem(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int? = null,
    val type: MenuItemType = MenuItemType.ITEM,
    val action: Intent? = null,
    val id: Int = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE)
)

class MenuItemDiffUtil: ItemCallback<MenuItem>() {

    override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem.id == newItem.id
    }

}

class MenuAdapter(
    private val onClick: (MenuItem) -> Unit
) : ListAdapter<MenuItem, RecyclerView.ViewHolder>(MenuItemDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        when(holder) {
            is MenuTitleViewHolder -> {
                holder.bind(item)
            }
            is MenuItemViewHolder -> {
                holder.bind(item)
                holder.itemView.setOnClickListener {
                    onClick.invoke(item)
                }
            }
            else -> Unit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            MenuItemType.HEADER.ordinal -> {
                MenuTitleViewHolder(parent.inflate(R.layout.menu_title_item))
            }
            else -> MenuItemViewHolder(parent.inflate(R.layout.menu_item))
        }
    }

}

class MenuItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val icon: AppCompatImageView by viewProvider(R.id.icon)
    private val title: AppCompatTextView by viewProvider(R.id.menu_item_title)

    fun bind(menuItem: MenuItem) {
        itemView.clipToOutline = true
        icon.clipToOutline = true

        menuItem.icon?.let {
            icon.setImageDrawable(ContextCompat.getDrawable(itemView.context, it))
        }

        icon.isInvisible = menuItem.icon == null
        title.text = itemView.resources.getString(menuItem.title)
    }

}

class MenuTitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(menuItem: MenuItem) {
        (itemView as? AppCompatTextView)?.text = itemView.resources.getString(menuItem.title)
    }

}
