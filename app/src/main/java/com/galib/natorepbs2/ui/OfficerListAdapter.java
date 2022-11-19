package com.galib.natorepbs2.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.galib.natorepbs2.BR;
import com.galib.natorepbs2.R;
import com.galib.natorepbs2.db.Employee;

public class OfficerListAdapter extends ListAdapter<Employee, OfficerListAdapter.EmployeeViewHolder> {
    public ClickListener onClickListener;

    public interface ClickListener {
        void onClickCall(String mobile);
        void onClickEmail(String email);
    }
    public OfficerListAdapter(@NonNull DiffUtil.ItemCallback<Employee> diffCallback, ClickListener listener) {
        super(diffCallback);
        onClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_officers;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new EmployeeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.bind(getItem(position), onClickListener);
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public EmployeeViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Employee employee, ClickListener clickListener) {
            binding.setVariable(BR.employee, employee);
            binding.setVariable(BR.clickListener, clickListener);
            binding.executePendingBindings();
        }
    }


    static class OfficerDiff extends DiffUtil.ItemCallback<Employee> {

        @Override
        public boolean areItemsTheSame(@NonNull Employee oldItem, @NonNull Employee newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Employee oldItem, @NonNull Employee newItem) {
            return oldItem.getMobile().equals(newItem.getMobile());
        }
    }
}

