package com.galib.natorepbs2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.galib.natorepbs2.db.Achievement;

public class AchievementAdapter extends ListAdapter<Achievement, AchievementAdapter.AchievementViewHolder> {

    public AchievementAdapter(@NonNull DiffUtil.ItemCallback<Achievement> diffCallback) {
        super(diffCallback);
    }

    @Override
    public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AchievementViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        Achievement current = getItem(position);
        holder.bind(current.getSerial(), current.getTitle(), current.getLastValue(), current.getCurValue(), current.getTotalValue());
    }

    static class WordDiff extends DiffUtil.ItemCallback<Achievement> {

        @Override
        public boolean areItemsTheSame(@NonNull Achievement oldItem, @NonNull Achievement newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Achievement oldItem, @NonNull Achievement newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }
    static class AchievementViewHolder extends RecyclerView.ViewHolder {
        private final TextView serialText;
        private final TextView titleText;
        private final TextView lastValueText;
        private final TextView curValueText;
        private final TextView totalValueText;


        private AchievementViewHolder(View itemView) {
            super(itemView);
            serialText = itemView.findViewById(R.id.serialTextView);
            titleText = itemView.findViewById(R.id.titleTextView);
            lastValueText = itemView.findViewById(R.id.lastValueTextView);
            curValueText = itemView.findViewById(R.id.curValueTextView);
            totalValueText = itemView.findViewById(R.id.totalValueTextView);
        }

        public void bind(String serial, String title, String last, String cur, String total) {
            serialText.setText(serial);
            titleText.setText(title);
            lastValueText.setText(last);
            curValueText.setText(cur);
            totalValueText.setText(total);
        }

        static AchievementViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.achievement_item, parent, false);
            return new AchievementViewHolder(view);
        }
    }
}

