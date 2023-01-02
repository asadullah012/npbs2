package com.galib.natorepbs2.ui

import android.text.BoringLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.R
import com.galib.natorepbs2.models.MyMenuItem

interface SettingsOnClickListener {
    fun settingsOnClick(name : String, isFavoriteAdapter: Boolean)
}

class SettingsAdapter(val listener: SettingsOnClickListener, private val isFavoriteAdapter: Boolean) : ListAdapter<MyMenuItem, SettingsAdapter.SettingsHolder>(MyMenuItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsHolder {
        return SettingsHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SettingsHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name, listener, isFavoriteAdapter)
    }

    class SettingsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val button: Button = itemView.findViewById(R.id.button)

        fun bind(text: String, listener: SettingsOnClickListener, isFavoriteAdapter: Boolean) {
            title.text = text
            button.setOnClickListener {
                listener.settingsOnClick(text, isFavoriteAdapter)
            }
        }

        companion object {
            fun create(parent: ViewGroup): SettingsHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_fragment, parent, false)
                return SettingsHolder(view)
            }
        }
    }

    class MyMenuItemComparator : DiffUtil.ItemCallback<MyMenuItem>() {
        override fun areItemsTheSame(oldItem: MyMenuItem, newItem: MyMenuItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MyMenuItem, newItem: MyMenuItem): Boolean {
            return oldItem.name == newItem.name
        }
    }
}