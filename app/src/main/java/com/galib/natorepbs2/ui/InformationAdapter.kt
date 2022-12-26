package com.galib.natorepbs2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.BR
import com.galib.natorepbs2.R
import com.galib.natorepbs2.models.Information
import com.galib.natorepbs2.ui.InformationAdapter.InformationViewHolder

class InformationAdapter : ListAdapter<Information, InformationViewHolder>(InformationDiff()) {
    override fun getItemViewType(position: Int): Int {
        return R.layout.item_information
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return InformationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class InformationViewHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(information: Information?) {
            binding.setVariable(BR.information, information)
            binding.executePendingBindings()
        }
    }

    class InformationDiff : DiffUtil.ItemCallback<Information>() {
        override fun areItemsTheSame(oldItem: Information, newItem: Information): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Information, newItem: Information): Boolean {
            return oldItem.title == newItem.title && oldItem.category == newItem.category
        }
    }
}