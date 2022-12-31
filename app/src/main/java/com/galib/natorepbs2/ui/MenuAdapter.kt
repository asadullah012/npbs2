package com.galib.natorepbs2.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.R

interface MenuOnClickListener {
    fun menuOnClick(menuText : String)
}

class MenuAdapter(val context:Context, val listener: MenuOnClickListener, private val dataSet:MutableList<String>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var drawableId: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(v)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.button.text = dataSet[position]
        if(drawableId != 0) {
            holder.menuIcon.setImageDrawable(context.getDrawable(drawableId))
            holder.menuIcon.visibility = View.VISIBLE
        }
        holder.card.setOnClickListener { listener.menuOnClick(dataSet[position]) }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setIcon(drawableId:Int){
        this.drawableId = drawableId
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
        var button: TextView = itemView.findViewById(R.id.menu_title)
        var card: CardView = itemView.findViewById(R.id.cardMenu)
        var menuIcon: ImageView = itemView.findViewById(R.id.menu_icon)
    }
}
