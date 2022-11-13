package com.galib.natorepbs2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.aboutUsBtn).setOnClickListener(v -> Utility.openActivity(this, AboutUsActivity.class));
    }
}