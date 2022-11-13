package com.galib.natorepbs2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        findViewById(R.id.atAGlanceBtn).setOnClickListener(v-> Utility.openActivity(this, AtAGlanceActivity.class));
        findViewById(R.id.visionMissionBtn).setOnClickListener(v -> Utility.openActivity(this, VisionMissionActivity.class));
    }
}