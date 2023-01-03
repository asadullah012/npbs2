package com.galib.natorepbs2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.BR
import com.galib.natorepbs2.R
import com.galib.natorepbs2.models.OfficeInformation

class OfficesAdapter(onClickListener: OfficeInfoOnClickListener) : RecyclerView.Adapter<OfficesAdapter.OfficeInformationViewHolder?>() {
    private var onClickListener : OfficeInfoOnClickListener
    init {
        this.onClickListener= onClickListener
    }
    interface OfficeInfoOnClickListener {
        fun onClickCall(mobile: String?)
        fun onClickEmail(email: String?)
        fun onClickMap(mapUrl: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficeInformationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return OfficeInformationViewHolder(binding)
    }
    override fun onBindViewHolder(holder: OfficeInformationViewHolder, position: Int) {
        holder.bind(differ.currentList[position], onClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_offices
    }

    class OfficeInformationViewHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(office: OfficeInformation?, officeClickListener: OfficeInfoOnClickListener?) {
            binding.setVariable(BR.office, office)
            binding.setVariable(BR.clickListener, officeClickListener)
            binding.executePendingBindings()
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<OfficeInformation>(){
        override fun areItemsTheSame(oldItem: OfficeInformation, newItem: OfficeInformation): Boolean {
            return  oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: OfficeInformation, newItem: OfficeInformation): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun getItemCount()=differ.currentList.size
}

