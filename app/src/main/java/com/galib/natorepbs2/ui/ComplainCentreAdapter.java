package com.galib.natorepbs2.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.db.ComplainCentre;

public class ComplainCentreAdapter extends ListAdapter<ComplainCentre, ComplainCentreAdapter.ComplainCentreViewHolder> {
    Context context;
    public ComplainCentreAdapter(Context context, @NonNull DiffUtil.ItemCallback<ComplainCentre> diffCallback) {
        super(diffCallback);
        this.context =context;
    }

    @Override
    public ComplainCentreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ComplainCentreViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplainCentreViewHolder holder, int position) {
        ComplainCentre current = getItem(position);
        holder.bind(context, current.getName(), current.getMobileNo());
    }

    static class WordDiff extends DiffUtil.ItemCallback<ComplainCentre> {

        @Override
        public boolean areItemsTheSame(@NonNull ComplainCentre oldItem, @NonNull ComplainCentre newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ComplainCentre oldItem, @NonNull ComplainCentre newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
    static class ComplainCentreViewHolder extends RecyclerView.ViewHolder {
        private final TextView complainCentreName;
        private final TextView complainCentreMobile;
        private final Button callButton;
        private ComplainCentreViewHolder(View itemView) {
            super(itemView);
            complainCentreName = itemView.findViewById(R.id.name);
            complainCentreMobile = itemView.findViewById(R.id.number);
            callButton = itemView.findViewById(R.id.callButton);
        }

        public void bind(Context context, String name, String mobileNo) {
            complainCentreName.setText(name);
            complainCentreMobile.setText(mobileNo);
            callButton.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobileNo));
                context.startActivity(intent);
            });
        }

        static ComplainCentreViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_complain_centre, parent, false);
            return new ComplainCentreViewHolder(view);
        }
    }
}
