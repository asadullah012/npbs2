package com.galib.natorepbs2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.galib.natorepbs2.sync.SyncTariff;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.aboutUsBtn).setOnClickListener(v -> Utility.openActivity(this, AboutUsActivity.class));
        //findViewById(R.id.serviceBtn).setOnClickListener(v -> new SyncTariff(this).execute());
    }
}