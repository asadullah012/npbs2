package com.galib.natorepbs2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ComplainCentreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_centre);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ComplainCentreAdapter adapter = new ComplainCentreAdapter(this, getResources().getStringArray(R.array.complain_centre_names), getResources().getStringArray(R.array.complain_centre_mobile_numbers));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}