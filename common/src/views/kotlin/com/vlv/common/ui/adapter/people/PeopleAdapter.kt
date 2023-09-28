package com.vlv.common.ui.adapter.people

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vlv.common.R
import com.vlv.common.data.people.People
import com.vlv.extensions.inflate

class PeopleAdapter(
    private val onClick: (People, View) -> Unit
) : ListAdapter<People, PeopleViewHolder>(PeopleDiffUtil()) {

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
        holder.avatar.setOnClickListener {
            onClick.invoke(item, it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        return PeopleViewHolder(parent.inflate(R.layout.common_people_listing_item))
    }

}