package com.galib.natorepbs2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.R
import com.galib.natorepbs2.models.MyMenuItem
import com.google.android.material.materialswitch.MaterialSwitch

interface SettingsOnClickListener {
    fun settingsOnClick(name : String, isFavoriteAdapter: Boolean)
}

class SettingsAdapter(val listener: SettingsOnClickListener) : ListAdapter<MyMenuItem, SettingsAdapter.SettingsHolder>(MyMenuItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsHolder {
        return SettingsHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SettingsHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, listener)
    }

    class SettingsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val switch: MaterialSwitch = itemView.findViewById(R.id.switch_menu)

        fun bind(currentItem:MyMenuItem, listener: SettingsOnClickListener) {
            title.text = currentItem.name
            switch.setOnCheckedChangeListener(null)
            switch.isChecked = currentItem.isFavorite
            switch.setOnCheckedChangeListener { _, isChecked ->
                listener.settingsOnClick(currentItem.name, isChecked)
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