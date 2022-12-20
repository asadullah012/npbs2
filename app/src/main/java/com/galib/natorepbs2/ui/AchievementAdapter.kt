package com.galib.natorepbs2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.R
import com.galib.natorepbs2.db.Achievement
import com.galib.natorepbs2.ui.AchievementAdapter.AchievementViewHolder


class AchievementAdapter : ListAdapter<Achievement, AchievementViewHolder>(AchievementDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        return AchievementViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(
            current!!.serial,
            current.title,
            current.lastValue,
            current.curValue,
            current.totalValue
        )
    }

    class AchievementDiffCallback : DiffUtil.ItemCallback<Achievement>(){
        override fun areItemsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return  oldItem.priority == newItem.priority
        }

        override fun areContentsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem.priority == newItem.priority
        }

    }
    class AchievementViewHolder private constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val serialText: TextView
        private val titleText: TextView
        private val lastValueText: TextView
        private val curValueText: TextView
        private val totalValueText: TextView

        init {
            serialText = itemView.findViewById(R.id.serialTextView)
            titleText = itemView.findViewById(R.id.titleTextView)
            lastValueText = itemView.findViewById(R.id.lastValueTextView)
            curValueText = itemView.findViewById(R.id.curValueTextView)
            totalValueText = itemView.findViewById(R.id.totalValueTextView)
        }

        fun bind(serial: String?, title: String?, last: String?, cur: String?, total: String?) {
            serialText.text = serial
            titleText.text = title
            lastValueText.text = last
            curValueText.text = cur
            totalValueText.text = total
        }

        companion object {
            fun create(parent: ViewGroup): AchievementViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_achievement, parent, false)
                return AchievementViewHolder(view)
            }
        }
    }
}