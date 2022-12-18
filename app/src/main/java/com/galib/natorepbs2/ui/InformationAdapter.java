package com.galib.natorepbs2.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.galib.natorepbs2.BR;
import com.galib.natorepbs2.R;
import com.galib.natorepbs2.db.Information;

public class InformationAdapter extends ListAdapter<Information, InformationAdapter.InformationViewHolder> {

    public InformationAdapter(@NonNull DiffUtil.ItemCallback<Information> diffCallback) {
        super(diffCallback);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_information;
    }

    @Override
    public InformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new InformationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class InformationViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public InformationViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Information information) {
            binding.setVariable(BR.information, information);
            binding.executePendingBindings();
        }
    }


    static class WordDiff extends DiffUtil.ItemCallback<Information> {

        @Override
        public boolean areItemsTheSame(@NonNull Information oldItem, @NonNull Information newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Information oldItem, @NonNull Information newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getCategory().equals(newItem.getCategory());
        }
    }
}

