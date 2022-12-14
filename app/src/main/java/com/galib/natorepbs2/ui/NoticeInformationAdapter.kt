package com.galib.natorepbs2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.R
import com.galib.natorepbs2.models.NoticeInformation

class NoticeInformationAdapter : RecyclerView.Adapter<NoticeInformationAdapter.TenderInformationViewHolder?>() {
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

        init {
            serialText = itemView.findViewById(R.id.serialTextView)
            titleText = itemView.findViewById(R.id.titleTextView)
            dateText = itemView.findViewById(R.id.dateTextView)
            pdfShow = itemView.findViewById(R.id.pdfShowButton)
        }

        fun bind(serial: Int, title: String, url:String?, date: String?, pdfUrl: String?) {
            serialText.text = (serial+1).toString()
            titleText.text = title
            dateText.text = date

            titleText.setOnClickListener {
                val action = NoticeInformationFragmentDirections.actionNoticeInformationFragmentToWebViewFragment(title, url, null, null)
                findNavController(itemView).navigate(action)
            }
            pdfShow.setOnClickListener {
                val action =
                    NoticeInformationFragmentDirections.actionNoticeInformationFragmentToPDFViewerFragment( title, pdfUrl, title + date)
                findNavController(itemView).navigate(action)
            }
            pdfShow.visibility = if(pdfUrl == null || pdfUrl.isEmpty())  View.INVISIBLE else View.VISIBLE
        }

        companion object {
            fun create(parent: ViewGroup): TenderInformationViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_tender_information, parent, false)
                return TenderInformationViewHolder(view)
            }
        }
    }
    private val differCallback = object : DiffUtil.ItemCallback<NoticeInformation>(){
        override fun areItemsTheSame(oldItem: NoticeInformation, newItem: NoticeInformation): Boolean {
            return  oldItem.priority == newItem.priority
        }

        override fun areContentsTheSame(oldItem: NoticeInformation, newItem: NoticeInformation): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun getItemCount()=differ.currentList.size
}