package com.galib.natorepbs2.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.R
import com.galib.natorepbs2.db.ComplainCentre
import com.galib.natorepbs2.ui.ComplainCentreAdapter.ComplainCentreViewHolder

class ComplainCentreAdapter(var context: Context) : ListAdapter<ComplainCentre, ComplainCentreViewHolder>(ComplainCentreDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplainCentreViewHolder {
        return ComplainCentreViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ComplainCentreViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(context, current!!.name, current.mobileNo)
    }

    class ComplainCentreViewHolder private constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val complainCentreName: TextView
        private val complainCentreMobile: TextView
        private val callButton: Button

        init {
            complainCentreName = itemView.findViewById(R.id.name)
            complainCentreMobile = itemView.findViewById(R.id.number)
            callButton = itemView.findViewById(R.id.callButton)
        }

        fun bind(context: Context, name: String?, mobileNo: String) {
            complainCentreName.text = name
            complainCentreMobile.text = mobileNo
            callButton.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_DIAL, Uri.parse(
                        "tel:$mobileNo"
                    )
                )
                context.startActivity(intent)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ComplainCentreViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_complain_centre, parent, false)
                return ComplainCentreViewHolder(view)
            }
        }
    }

    class ComplainCentreDiff : DiffUtil.ItemCallback<ComplainCentre>() {
        override fun areItemsTheSame(oldItem: ComplainCentre, newItem: ComplainCentre): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ComplainCentre, newItem: ComplainCentre): Boolean {
            return oldItem.name == newItem.name
        }
    }
}