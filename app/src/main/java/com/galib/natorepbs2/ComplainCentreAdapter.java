package com.galib.natorepbs2;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ComplainCentreAdapter extends RecyclerView.Adapter<ComplainCentreAdapter.ViewHolder>{
    String[] names;
    String[] numbers;
    Context context;
    public ComplainCentreAdapter(Context context, String []names, String[] numbers){
        this.names = names;
        this.numbers = numbers;
        this.context = context;
    }
    @NonNull
    @Override
    public ComplainCentreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComplainCentreAdapter.ViewHolder holder, int position) {
        holder.name.setText(names[position]);
        holder.number.setText(numbers[position]);
        holder.callButton.setOnClickListener(view -> {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:" + numbers[position]));
            context.startActivity(phoneIntent);
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number;
        public Button callButton;
        public ViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.number = itemView.findViewById(R.id.number);
            this.callButton = itemView.findViewById(R.id.callButton);
        }
    }
}
