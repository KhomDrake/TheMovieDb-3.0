package com.vlv.imperiyasample

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

class Component(
    val title: String,
    val activity: KClass<*>
)

class ComponentsAdapter(
    private val components: List<Component>
) : RecyclerView.Adapter<ComponentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        return ComponentViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.components_button_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return components.size
    }

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        holder.bind(components[position])
    }

}

class ComponentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(component: Component) {
        (itemView as? AppCompatButton)?.apply {
            text = component.title
            setOnClickListener {
                context.startActivity(
                    Intent(
                        context,
                        component.activity.java
                    )
                )
            }
        }
    }

}