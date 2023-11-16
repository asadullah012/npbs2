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
import com.galib.natorepbs2.utils.Utility
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class InterruptionsAdapter(var context: Context) : ListAdapter<Interruption, InterruptionsAdapter.InterruptionViewHolder>(
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
        val interruption = getItem(position)
        holder.bind(interruption)
        holder.itemView.setOnClickListener {
            val title = "বিদ্যুৎ বিভ্রাটের বিজ্ঞপ্তি"
            val description = "নাটোর পল্লী বিদ্যুৎ সমিতি-২ এর ${interruption.office} অফিসের আওতাধীন সম্মানিত গ্রাহকগণের সদয় অবগতির জন্য জানানো যাচ্ছে যে আগামী ${interruption.date} ইং তারিখে ${interruption.reason} জন্য ${interruption.substation} উপকেন্দ্রের ${interruption.feeder} ফিডারের আওতায় ${interruption.area} গ্রামে সকাল ${interruption.startTime} টা থেকে বিকেল ${interruption.endTime} ঘটিকা পর্যন্ত বিদ্যুৎ সরবরাহ বন্ধ থাকবে। বিদ্যুৎ সরবরাহ সাময়িক ভাবে বন্ধের জন্য কর্তৃপক্ষ আন্তরিক ভাবে দুঃখিত।\nপ্রচারে,\nকর্তৃপক্ষ,\nনাটোর পল্লী বিদ্যুৎ সমিতি-২"
            Log.d("TAG", "onBindViewHolder: $title $description")

            MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(description)
                .show()
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