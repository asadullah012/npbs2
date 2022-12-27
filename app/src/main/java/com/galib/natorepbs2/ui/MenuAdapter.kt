package com.galib.natorepbs2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.R

interface MenuOnClickListener {
    fun menuOnClick(menuText : String)
}

class MenuAdapter(val listener: MenuOnClickListener) : ListAdapter<String, MenuAdapter.MenuViewHolder>(StringDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, listener)
    }

    class StringDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    class MenuViewHolder private constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val menuBtn: AppCompatButton

        init {
            menuBtn = itemView.findViewById(R.id.menu_btn)
        }

        fun bind(title: String, listener: MenuOnClickListener) {
            menuBtn.text = title
            menuBtn.setOnClickListener {
                listener.menuOnClick(title)
            }
        }

        companion object {
            fun create(parent: ViewGroup): MenuViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_menu, parent, false)
                return MenuViewHolder(view)
            }
        }
    }
}
