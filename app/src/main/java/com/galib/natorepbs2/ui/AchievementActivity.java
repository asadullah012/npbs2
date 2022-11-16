package com.galib.natorepbs2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.galib.natorepbs2.viewmodel.AchievementViewModel;
import com.galib.natorepbs2.R;
import com.galib.natorepbs2.sync.SyncAchievement;

public class AchievementActivity extends AppCompatActivity {
    private AchievementViewModel achievementViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        achievementViewModel = new ViewModelProvider(this).get(AchievementViewModel.class);
        startSync(achievementViewModel);
        RecyclerView recyclerView = findViewById(R.id.achievementRecyclerView);
        final AchievementAdapter adapter = new AchievementAdapter(new AchievementAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        achievementViewModel.getAllAchievement().observe(this, achievements -> {
            adapter.submitList(achievements);
        });
    }
    protected void startSync(AchievementViewModel achievementViewModel){
        new SyncAchievement(achievementViewModel).execute();
    }
}