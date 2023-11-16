package com.galib.natorepbs2.ui


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.BR
import com.galib.natorepbs2.R
import com.galib.natorepbs2.models.Interruption

class InterruptionsAdapter(context: Context) : ListAdapter<Interruption, InterruptionsAdapter.InterruptionViewHolder>(
    InterruptionDiff()
) {
    override fun getItemViewType(position: Int): Int {
        return R.layout.item_interruption
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterruptionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        val viewHolder = InterruptionViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: InterruptionViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            Log.d("TAG", "onBindViewHolder: ${getItem(position).creationTime}")
        }
    }

    class InterruptionViewHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(interruption: Interruption?) {
            binding.setVariable(BR.interruption, interruption)
            binding.executePendingBindings()
        }
    }

    class InterruptionDiff : DiffUtil.ItemCallback<Interruption>() {
        override fun areItemsTheSame(oldItem: Interruption, newItem: Interruption): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Interruption, newItem: Interruption): Boolean {
            return oldItem.creationTime == newItem.creationTime
        }
    }
}