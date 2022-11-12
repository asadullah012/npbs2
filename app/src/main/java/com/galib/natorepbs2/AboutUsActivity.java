package com.galib.natorepbs2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        findViewById(R.id.atAGlanceBtn).setOnClickListener(v-> openAtAGlanceActivity());
    }

    private void openAtAGlanceActivity() {
        Intent intent = new Intent(this, AtAGlanceActivity.class);
        startActivity(intent);
    }
}