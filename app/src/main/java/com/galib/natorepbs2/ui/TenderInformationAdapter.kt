package com.galib.natorepbs2.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.R
import com.galib.natorepbs2.db.TenderInformation

class TenderInformationAdapter() : RecyclerView.Adapter<TenderInformationAdapter.TenderInformationViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenderInformationViewHolder {
        return TenderInformationViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TenderInformationViewHolder, position: Int) {
        val current = differ.currentList[position]
        if (current != null) {
            holder.bind(
                current.priority,
                current.title,
                current.url,
                current.date,
                current.pdfUrl
            )
        }
    }

    class TenderInformationViewHolder private constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val serialText: TextView
        private val titleText: TextView
        private val dateText: TextView
        private val pdfShow:ImageView
//        private val pdfText: TextView

        init {
            serialText = itemView.findViewById(R.id.serialTextView)
            titleText = itemView.findViewById(R.id.titleTextView)
            dateText = itemView.findViewById(R.id.dateTextView)
            pdfShow = itemView.findViewById(R.id.pdfShowButton)
//            pdfText = itemView.findViewById(R.id.pdfUrlTextView)
        }

        fun bind(serial: Int, title: String?, url:String?, date: String?, pdfUrl: String?) {
            serialText.text = serial.toString()
            titleText.text = title
            dateText.text = date
            pdfShow.setOnClickListener { Log.d("TAG", "bind: pdf button clicked $pdfUrl") }
        }

        companion object {
            fun create(parent: ViewGroup): TenderInformationViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_tender_information, parent, false)
                return TenderInformationViewHolder(view)
            }
        }
    }
    private val differCallback = object : DiffUtil.ItemCallback<TenderInformation>(){
        override fun areItemsTheSame(oldItem: TenderInformation, newItem: TenderInformation): Boolean {
            return  oldItem.priority == newItem.priority
        }

        override fun areContentsTheSame(oldItem: TenderInformation, newItem: TenderInformation): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun getItemCount()=differ.currentList.size
}