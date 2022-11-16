package com.galib.natorepbs2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.Utility;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        findViewById(R.id.atAGlanceBtn).setOnClickListener(v-> Utility.openActivity(this, AtAGlanceActivity.class));
        findViewById(R.id.visionMissionBtn).setOnClickListener(v -> Utility.openActivity(this, VisionMissionActivity.class));
        findViewById(R.id.achievementBtn).setOnClickListener(v -> Utility.openActivity(this, AchievementActivity.class));
        findViewById(R.id.complainCentreBtn).setOnClickListener(v -> Utility.openActivity(this, ComplainCentreActivity.class));
    }
}