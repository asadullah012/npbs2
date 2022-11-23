package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.viewmodel.AchievementViewModel;

public class AchievementActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        AchievementViewModel achievementViewModel = new ViewModelProvider(this).get(AchievementViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.achievementRecyclerView);
        final AchievementAdapter adapter = new AchievementAdapter(new AchievementAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        achievementViewModel.getAllAchievement().observe(this, adapter::submitList);
    }
}