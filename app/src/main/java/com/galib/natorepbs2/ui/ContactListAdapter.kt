package com.galib.natorepbs2.ui

import androidx.recyclerview.widget.DiffUtil
import com.galib.natorepbs2.db.Employee
import com.galib.natorepbs2.ui.ContactListAdapter.ClickListener
import com.galib.natorepbs2.ui.ContactListAdapter.EmployeeViewHolder
import com.galib.natorepbs2.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import com.galib.natorepbs2.BR
import com.squareup.picasso.Picasso

class ContactListAdapter(
    diffCallback: DiffUtil.ItemCallback<Employee?>,
    var onClickListener: ClickListener
) : ListAdapter<Employee?, EmployeeViewHolder>(diffCallback) {
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

        companion object {
            @BindingAdapter("setProfilePhoto")
            fun setProfilePhoto(imageView: ImageView?, imageUrl: String?) {
                Picasso.get()
                    .load(if (imageUrl == null || imageUrl.length == 0) null else imageUrl)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                    .into(imageView)
            }
        }
    }

    internal class OfficerDiff : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.mobile == newItem.mobile
        }
    }
}