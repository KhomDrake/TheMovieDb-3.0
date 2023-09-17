package com.vlv.people.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.extensions.inflate
import com.vlv.extensions.loadUrl
import com.vlv.people.R
import com.vlv.people.data.People

class PeopleDiffUtil: ItemCallback<People>() {

    override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
        return oldItem.name == newItem.name && oldItem.department == newItem.department
    }

    override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
        return oldItem.id == newItem.id
    }

}

const val VIEW_TYPE_PEOPLE = 2398

class PeoplePagingAdapter: PagingDataAdapter<People, PeopleViewHolder>(PeopleDiffUtil()) {

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return if(itemCount == position || peek(position) == null) super.getItemViewType(position)
        else VIEW_TYPE_PEOPLE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        return PeopleViewHolder(parent.inflate(R.layout.people_listing_item))
    }

}

class PeopleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: AppCompatTextView by viewProvider(R.id.name)
    private val avatar: AppCompatImageView by viewProvider(R.id.avatar)

    fun bind(people: People) {
        avatar.clipToOutline = true
        name.text = people.name
        people.profilePath?.loadUrl(avatar)
    }

}
