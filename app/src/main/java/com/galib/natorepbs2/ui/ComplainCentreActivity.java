package com.galib.natorepbs2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.galib.natorepbs2.viewmodel.ComplainCentreViewModel;
import com.galib.natorepbs2.R;

public class ComplainCentreActivity extends AppCompatActivity {
    ComplainCentreViewModel complainCentreViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_centre);
        complainCentreViewModel = new ViewModelProvider(this).get(ComplainCentreViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ComplainCentreAdapter adapter = new ComplainCentreAdapter(this, new ComplainCentreAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complainCentreViewModel.getAllComplainCentre().observe(this, adapter::submitList);
    }
}