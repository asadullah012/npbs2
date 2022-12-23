package com.galib.natorepbs2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galib.natorepbs2.BR
import com.galib.natorepbs2.R
import com.galib.natorepbs2.db.Employee
import com.galib.natorepbs2.ui.ContactListAdapter.EmployeeViewHolder
import com.squareup.picasso.Picasso

@BindingAdapter("setProfilePhoto")
fun setProfilePhoto(imageView: ImageView?, imageUrl: String?) {
    Picasso.get()
        .load(if (imageUrl == null || imageUrl.isEmpty()) null else imageUrl)
        .placeholder(R.mipmap.ic_launcher_round)
        .error(R.mipmap.ic_launcher_round)
        .into(imageView)
}

class ContactListAdapter(
    var onClickListener: ClickListener
) : ListAdapter<Employee, EmployeeViewHolder>(OfficerDiff()) {
    interface ClickListener {
        fun onClickCall(mobile: String?)
        fun onClickEmail(email: String?)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_contact
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class EmployeeViewHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(employee: Employee?, clickListener: ClickListener?) {
            binding.setVariable(BR.employee, employee)
            binding.setVariable(BR.clickListener, clickListener)
            binding.executePendingBindings()
        }
    }

    class OfficerDiff : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.mobile == newItem.mobile
        }
    }
}