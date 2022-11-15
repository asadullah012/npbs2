package com.galib.natorepbs2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.galib.natorepbs2.db.Information;

public class AtAGlanceAdapter extends ListAdapter<Information, AtAGlanceAdapter.InformationViewHolder> {

    public AtAGlanceAdapter(@NonNull DiffUtil.ItemCallback<Information> diffCallback) {
        super(diffCallback);
    }

    @Override
    public InformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return InformationViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position) {
        Information current = getItem(position);
        holder.bind(current.getTitle(), current.getDescription(), current.getPriority());
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
    static class InformationViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView descText;
        private final TextView priorityText;

        private InformationViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleTextView);
            descText = itemView.findViewById(R.id.descTextView);
            priorityText = itemView.findViewById(R.id.priorityTextView);
        }

        public void bind(String title, String desc, int priority) {
            titleText.setText(title);
            descText.setText(desc);
            priorityText.setText(String.valueOf(priority));
        }

        static InformationViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ataglance, parent, false);
            return new InformationViewHolder(view);
        }
    }
}

