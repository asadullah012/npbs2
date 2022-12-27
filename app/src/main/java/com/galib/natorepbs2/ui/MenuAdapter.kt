package com.galib.natorepbs2.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.R

interface MenuOnClickListener {
    fun menuOnClick(v: View, menuText : String)
}

class MenuAdapter(val context:Context, val listener: MenuOnClickListener, private val dataSet:MutableList<String>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(v)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.button.text = dataSet[position]
        holder.button.setOnClickListener { listener.menuOnClick(holder.button, dataSet[position]) }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun remove(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }

    fun add(text: String, position: Int) {
        dataSet.add(position, text)
        notifyItemInserted(position)
    }

    class MenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var button: AppCompatButton = itemView.findViewById<View>(R.id.menu_btn) as AppCompatButton

    }
}
