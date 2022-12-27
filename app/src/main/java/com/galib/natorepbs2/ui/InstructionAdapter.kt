package com.galib.natorepbs2.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.R
import com.galib.natorepbs2.models.Instruction
import java.io.InputStream


class InstructionAdapter(private val context: Context) : ListAdapter<Instruction, InstructionAdapter.InstructionViewHolder>(InstructionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        return InstructionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(context,
            current.title,
            current.imagePath
        )
    }

    class InstructionDiffCallback : DiffUtil.ItemCallback<Instruction>(){
        override fun areItemsTheSame(oldItem: Instruction, newItem: Instruction): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Instruction, newItem: Instruction): Boolean {
            return oldItem.imagePath == newItem.imagePath
        }

    }
    class InstructionViewHolder private constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView
        private val imageView: ImageView
        init {
            titleText = itemView.findViewById(R.id.titleTextView)
            imageView = itemView.findViewById(R.id.imageView)
        }

        fun bind(context: Context, title: String?, imagePath: String) {
            titleText.text = title
            val ins:InputStream = context.assets.open(imagePath)
            imageView.setImageDrawable(Drawable.createFromStream(ins, null))
            ins.close()
        }

        companion object {
            fun create(parent: ViewGroup): InstructionViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_instruction, parent, false)
                return InstructionViewHolder(view)
            }
        }
    }
}